package com.example.library.repository;

import com.example.library.entity.Order;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserId(Long userId);
    Optional<Order> findByUserUsername(String username);
}
