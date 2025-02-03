package com.example.library.repository;

import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean findByTitleAndAuthor(String author, String title);
    List<Book> findByTitleContainingIgnoreCase(String title);
}
