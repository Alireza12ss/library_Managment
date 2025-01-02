package com.example.library.repository;

import com.example.library.entity.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
}
