package com.codegym.book.repository;

import com.codegym.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Page<Book> findAllByBooksCodeContains(String code, Pageable pageable);
}
