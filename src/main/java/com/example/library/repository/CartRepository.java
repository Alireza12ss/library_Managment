package com.example.library.repository;

import com.example.library.entity.Cart;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.book.id = :bookId")
    Optional<Cart> findByUserIdAndBookId(@Param("userId") Long userId, @Param("bookId") Long bookId);
}
