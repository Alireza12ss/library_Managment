package com.example.library.controller;

import com.example.library.dto.*;
import com.example.library.entity.Book;
import com.example.library.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    private final BookService bookService;
    private final BookRequestService bookRequestService;
    private final BookGroupService bookGroupService;
    private final AuthService userService;
    private final WishlistService wishlistService;
    private final OrderService orderService;
    private final CartService cartService;

    // ================= Book Management =================
    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.addBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // ================= Book Group Management =================
    @GetMapping("/book-groups")
    public List<BookGroupDto> getAllBookGroups() {
        return bookGroupService.getAllBookGroups();
    }

    @PostMapping("/book-groups")
    public ResponseEntity<BookGroupDto> addBookGroup(@RequestBody BookGroupDto bookGroupDto) {
        return new ResponseEntity<>(bookGroupService.createBookGroup(bookGroupDto), HttpStatus.CREATED);
    }

    @PutMapping("/book-groups/{id}")
    public ResponseEntity<BookGroupDto> updateBookGroup(@PathVariable Long id, @RequestBody BookGroupDto bookGroupDto) {
        return ResponseEntity.ok(bookGroupService.updateBookGroup(id, bookGroupDto));
    }

    @DeleteMapping("/book-groups/{id}")
    public ResponseEntity<Void> deleteBookGroup(@PathVariable Long id) {
        bookGroupService.deleteBookGroup(id);
        return ResponseEntity.noContent().build();
    }

    // ================= User Management =================
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ================= Wishlist Management =================
    @GetMapping("/wishlists")
    public List<WishlistDto> getAllWishlists() {
        return wishlistService.getAllWishLists();
    }

    @GetMapping("/wishlists/user/{userId}")
    public List<BookDto> getWishlistByUser(@PathVariable Long userId) {
        return wishlistService.getUserWishlist(userId);
    }

    @DeleteMapping("/wishlists/{wishlistId}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlistItem(wishlistId);
        return ResponseEntity.noContent().build();
    }
    // ================= book request =====================

    @PutMapping("/book-requests/{requestId}/status")
    public ResponseEntity<Void> updateBookRequestStatus(
            @PathVariable Long requestId,
            @RequestParam String status) {
        bookRequestService.updateRequestStatus(requestId, status);
        return ResponseEntity.noContent().build();
    }

    // ================= Order Management =================
    @GetMapping("/orders")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/user/{userId}")
    public List<OrderDto> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId, @RequestParam boolean status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.noContent().build();
    }

    // ================= Cart Management =================
    @GetMapping("/carts")
    public List<CartDto> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/carts/user/{userId}")
    public CartDto getCartByUser(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

}
