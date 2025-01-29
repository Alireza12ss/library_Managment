package com.example.library.repository;

import com.example.library.entity.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
    Collection<BookRequest> findByUserId(Long userId);
    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.title = :title")
    Optional<BookRequest> findByAuthorAndTitle(@Param("author") String author, @Param("title") String title);
}
