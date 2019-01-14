package com.codegym.book.service;

import com.codegym.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<Book> findAll(Pageable pageable);

    Book findById(Long id);

    void save(Book book);

    void remove(Long id);

    Page<Book> findAllByCode(String search, Pageable pageable);
}
