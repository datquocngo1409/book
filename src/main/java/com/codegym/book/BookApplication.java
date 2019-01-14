package com.codegym.book;

import com.codegym.book.service.BookService;
import com.codegym.book.service.CategoryService;
import com.codegym.book.service.impl.BookServiceImpl;
import com.codegym.book.service.impl.CategoryServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@Bean
	public BookService bookService(){
		return new BookServiceImpl();
	}

	@Bean
	public CategoryService categoryService(){
		return new CategoryServiceImpl();
	}
}

