package com.example.library.service;

import com.example.library.dto.ResponseCartDto;
import com.example.library.entity.*;
import com.example.library.mapper.CartMapper;
import com.example.library.repository.*;
import com.example.library.dto.ResultDto;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public ResultDto<ResponseCartDto> getByUserId(Long userId) {
        try {
            var cart = getCartByUserId(userId);
            return ResponseUtil.success(cartMapper.toDto(cart));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<ResponseCartDto> getForUser() {
        try {
            long userId = getCurrentUserId();
            var cart = getCartByUserId(userId);
            ResponseCartDto response = cartMapper.toDto(cart);
            return ResponseUtil.success(response);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }

    }

    @Transactional
    public ResultDto<Boolean> add(Long bookId, int quantity) {
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
    public ResultDto<ResponseCartDto> deleteItem(Long itemId) {
        try {
            var userId = getCurrentUserId();
            var cart = getCartByUserId(userId);

            var cartItemToRemove = cartItemRepository.findCartItemByCart_IdAndAndId(cart.getId() , itemId)
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
    public ResultDto<Boolean> delete(Long cartId) {
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

    public ResultDto<List<ResponseCartDto>> getAll() {
        try {
            var carts = cartRepository.findAll().stream()
                    .map(cartMapper::toDto)
                    .toList();
            return ResponseUtil.success(carts);
        }catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    private Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException.NotFound("Cart not found for user ID: " + userId));
    }

    private Cart createNewCartForUser(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }
}
