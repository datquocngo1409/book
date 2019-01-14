package com.codegym.book.controller;

import com.codegym.book.model.Book;
import com.codegym.book.model.Category;
import com.codegym.book.service.BookService;
import com.codegym.book.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/books")
    public ModelAndView showList(@PageableDefault(5) Pageable pageable, @RequestParam("s") Optional<String> s) {
        String search = s.toString().substring(9, s.toString().length()-1);
        if (search.equals("empt")) search = "";
        Page<Book> books;
        if (!s.isPresent()) {
            books = bookService.findAll(pageable);
        }else {
            books = bookService.findAllByCode(search, pageable);
        }
        ModelAndView modelAndView = new ModelAndView("book/list");
        modelAndView.addObject("books", books);
        modelAndView.addObject("search", search);
        return modelAndView;
    }

    @GetMapping("/create-book")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("book/create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }

    @PostMapping("/create-book")
    public ModelAndView createBook(@Validated @ModelAttribute("book") Book book, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("book/create");
        if (!bindingResult.hasFieldErrors()){
            bookService.save(book);
            modelAndView.addObject("book", book);
            modelAndView.addObject("mess", "Created!");
        }else {
            modelAndView.addAllObjects(bindingResult.getModel());
            modelAndView.addObject("book", new Book());
        }
        return modelAndView;
    }

    @GetMapping("/edit-book/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        ModelAndView modelAndView =  new ModelAndView("book/edit");
        Book book = bookService.findById(id);
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @PostMapping("/edit-book/{id}")
    public ModelAndView editBook(@Validated @ModelAttribute("book") Book book, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("book/edit");
        if (!bindingResult.hasFieldErrors()) {
            bookService.save(book);
            modelAndView.addObject("book", book);
            modelAndView.addObject("mess", "Edited!");
        }else {
            modelAndView.addAllObjects(bindingResult.getModel());
        }
        return modelAndView;
    }

    @GetMapping("/delete-book/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Book book = bookService.findById(id);
        ModelAndView modelAndView = new ModelAndView("book/delete");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @PostMapping("/delete-book")
    public String deleteBook(@ModelAttribute("book") Book book) {
        bookService.remove(book.getId());
        return "redirect:books";
    }
}
