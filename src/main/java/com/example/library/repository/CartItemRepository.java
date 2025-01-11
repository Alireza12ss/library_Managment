package com.example.library.repository;

import com.example.library.entity.CartItem;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
