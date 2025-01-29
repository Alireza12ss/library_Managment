package com.example.library.service;

import com.example.library.dto.CartDto;
import com.example.library.entity.*;
import com.example.library.mapper.CartMapper;
import com.example.library.repository.*;
import com.example.library.util.ApiResponse;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService extends SuperService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    public CartService(UserRepository userRepository, CartRepository cartRepository, BookRepository bookRepository, UserRepository userRepository1, CartItemRepository cartItemRepository, CartMapper cartMapper) {
        super(userRepository);
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository1;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
    }

    public ApiResponse<CartDto> getCartByUserId(Long userId) {
        try {
            var cart = getCartByUserIdInternal(userId);
            return ResponseUtil.success(cartMapper.toDto(cart));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ApiResponse<CartDto> getCartByUserId() {
        var userId = getCurrentUserId();
        return getCartByUserId(userId);
    }

    @Transactional
    public ApiResponse<Boolean> addItemToCart(Long bookId, int quantity) {
        try {
            var username = getUsername();
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomException.NotFound("User not found: " + username));
            var cart = cartRepository.findByUserId(user.getId())
                    .orElseGet(() -> createNewCartForUser(user));
            var book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new CustomException.NotFound("Book not found with ID: " + bookId));
            Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndBook(cart, book);
            CartItem cartItem;
            if (existingCartItem.isPresent()) {
                cartItem = existingCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                cartItem = new CartItem();
                cartItem.setBook(book);
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
            }
            cartItemRepository.save(cartItem);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ApiResponse<CartDto> removeItemFromCart(Long itemId) {
        try {
            var userId = getCurrentUserId();
            var cart = getCartByUserIdInternal(userId);

            var cartItemToRemove = cartItemRepository.findByCartIdAndItemId(cart.getId() , itemId)
                            .orElseThrow(() -> new CustomException.NotFound("cart item not found"));

            cartItemRepository.delete(cartItemToRemove);
            cart.getCartItems().remove(cartItemToRemove);
            cartRepository.save(cart);

            return ResponseUtil.success(cartMapper.toDto(cart));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Boolean> clearCartForAdmin(Long cartId) {
        try {
            var cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new CustomException.NotFound("Cart not found with ID: " + cartId));
            cartItemRepository.deleteAll(cart.getCartItems());
            cart.getCartItems().clear();
            cartRepository.delete(cart);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Boolean> clearCart(Long cartId) {
        try {
            var userId = getCurrentUserId();
            var cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new CustomException.NotFound("Cart not found with ID: " + cartId));
            if (!cart.getUser().getId().equals(userId)) {
                throw new CustomException.Forbidden("You do not have permission to clear this cart");
            }
            cartItemRepository.deleteAll(cart.getCartItems());
            cart.getCartItems().clear();
            cartRepository.delete(cart);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ApiResponse<List<CartDto>> getAllCarts() {
        try {
            var carts = cartRepository.findAll().stream()
                    .map(cartMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseUtil.success(carts);
        }catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    private Cart getCartByUserIdInternal(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException.NotFound("Cart not found for user ID: " + userId));
    }

    private Cart createNewCartForUser(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }
}
