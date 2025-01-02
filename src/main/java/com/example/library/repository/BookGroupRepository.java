package com.example.library.repository;

import com.example.library.entity.BookGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookGroupRepository extends JpaRepository<BookGroup, Long> {
    Optional<BookGroup> findByName(String name);
}
