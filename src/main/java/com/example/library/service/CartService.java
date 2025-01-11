package com.example.library.service;

import com.example.library.dto.CartDto;
import com.example.library.dto.CartItemDto;
import com.example.library.entity.*;
import com.example.library.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    // Get Cart by userId (or create a new cart if not found)
    public CartDto getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    // Create a new cart if none exists for the user
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
                    cartRepository.save(newCart); // Save the new cart
                    return newCart;
                });
        return mapToCartDTO(cart);
    }

    // Add item to cart (create cart if none exists)
    public void addItemToCart(String username, Long bookId, int quantity) {
        // Fetch the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the user's cart
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    // Create a new cart for the user if none exists
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // Fetch the book by ID
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Create a new CartItem and associate it with the cart
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart); // Associate with the cart
        cartItem.setOrder(null); // Default order can be null for now

        // Save the cart item
        cartItemRepository.save(cartItem);
    }

    // Remove item from cart
    public CartDto removeItemFromCart(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        // Delete the CartItem from the repository and remove it from the cart
        cartItemRepository.delete(cartItemToRemove);
        cart.getCartItems().remove(cartItemToRemove);

        // Save the updated cart
        cartRepository.save(cart);
        return mapToCartDTO(cart);
    }

    // Clear the cart
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Remove and delete all cart items
        cart.getCartItems().forEach(cartItemRepository::delete);
        cart.getCartItems().clear();

        // Save the cart after clearing items
        cartRepository.save(cart);
    }

    // Map Cart to CartDto
    private CartDto mapToCartDTO(Cart cart) {
        return new CartDto(
                cart.getUser().getId(),
                cart.getCartItems().stream()
                        .map(this::mapToCartItemDTO)
                        .collect(Collectors.toList()),
                cart.getCartItems().stream()
                        .mapToLong(item -> item.getBook().getPrice() * item.getQuantity())
                        .sum()
        );
    }

    // Map CartItem to CartItemDto
    private CartItemDto mapToCartItemDTO(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getBook().getId(),
                cartItem.getBook().getTitle(),
                cartItem.getQuantity(),
                cartItem.getBook().getPrice(),
                cartItem.getBook().getPrice() * cartItem.getQuantity()
        );
    }

    // Get all carts (Admin functionality)
    public List<CartDto> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(this::mapToCartDTO)
                .collect(Collectors.toList());
    }
}
