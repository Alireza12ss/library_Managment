package com.example.library.repository;

import com.example.library.entity.Book;
import com.example.library.entity.Cart;
import com.example.library.entity.CartItem;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndBook(Cart cart, Book book);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.id = :itemId")
    Optional<CartItem> findByCartIdAndItemId(@Param("cartId") Long cartId, @Param("itemId") Long itemId);
}
