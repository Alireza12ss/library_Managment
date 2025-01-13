package com.example.library.service;

import com.example.library.dto.CartDto;
import com.example.library.entity.*;
import com.example.library.mapper.CartMapper;
import com.example.library.repository.*;
import com.example.library.exception.CartNotFoundException;
import com.example.library.exception.CartItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService extends SuperService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UserRepository userRepository, CartRepository cartRepository,
                       BookRepository bookRepository, UserRepository userRepository1,
                       CartItemRepository cartItemRepository) {
        super(userRepository);
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository1;
        this.cartItemRepository = cartItemRepository;
    }

    public CartDto getCartByUserId(Long userId) {
        return getCartDto(userId);
    }

    public CartDto getCartByUserId() {
        Long userId = getCurrentUserId();
        return getCartDto(userId);
    }

    private CartDto getCartDto(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user ID: " + userId));
        return CartMapper.mapToCartDto(cart);
    }

    public void addItemToCart(Long bookId, int quantity) {
        String username = getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        cartItem.setOrder(null);
        cartItemRepository.save(cartItem);
    }

    public CartDto removeItemFromCart(Long itemId) {
        Long userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user ID: " + userId));
        CartItem cartItemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with ID: " + itemId));
        cartItemRepository.delete(cartItemToRemove);
        cart.getCartItems().remove(cartItemToRemove);
        cartRepository.save(cart);
        return CartMapper.mapToCartDto(cart);
    }

    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    public List<CartDto> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(CartMapper::mapToCartDto)
                .collect(Collectors.toList());
    }
}
