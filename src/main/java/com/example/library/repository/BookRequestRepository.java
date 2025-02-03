package com.example.library.repository;

import com.example.library.entity.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.List;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
    Collection<BookRequest> findByUserId(Long userId);
    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.title = :title")
    Optional<BookRequest> findByAuthorAndTitle(@Param("author") String author, @Param("title") String title);
    @Query("SELECT br FROM BookRequest br WHERE br.user.username = :username")
    List<BookRequest> findByUsername(@Param("username") String username);
    @Transactional
    @Modifying
    @Query("DELETE FROM BookRequest br WHERE br.id = :id")
    int deleteByIdAndReturnAffectedRows(@Param("id") Long id);

}
