package com.example.library.service;

import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.dto.WishlistDto;
import com.example.library.entity.Wishlist;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.WishlistMapper;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.repository.WishlistRepository;
import com.example.library.dto.ResultDto;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService extends SuperService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final WishlistMapper wishlistMapper;
    private final BookMapper bookMapper;

    public WishlistService(UserRepository userRepository, WishlistRepository wishlistRepository,
                           UserRepository userRepository1, BookRepository bookRepository,
                           WishlistMapper wishlistMapper , BookMapper bookMapper) {
        super(userRepository);
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository1;
        this.bookRepository = bookRepository;
        this.wishlistMapper = wishlistMapper;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public ResultDto<Boolean> create(Long bookId) {
        try {
            var userId = getCurrentUserId();

            if (wishlistRepository.existsByUserIdAndBookId(userId, bookId)) {
                throw new CustomException.BadRequest("Book already in wishlist");
            }

            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException.NotFound("User not found with ID: " + userId));

            var book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new CustomException.NotFound("Book not found with ID: " + bookId));

            var wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist.setBook(book);

            wishlistRepository.save(wishlist);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ResultDto<Boolean> delete(Long bookId) {
        try {
            var userId = getCurrentUserId();

            if (!wishlistRepository.existsByUserIdAndBookId(userId, bookId)) {
                throw new CustomException.NotFound("Book not in wishlist");
            }

            wishlistRepository.deleteByUserIdAndBookId(userId, bookId);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<List<ResponseBookDto>> getForUser() {
        try {
            var userId = getCurrentUserId();
            var wishlist = wishlistRepository.findByUserId(userId).stream()
                    .map(item -> bookMapper.toDto(item.getBook()))
                    .collect(Collectors.toList());
            return ResponseUtil.success(wishlist);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<List<ResponseBookDto>> getForAdmin(Long userId) {
        try {
            var wishlist = wishlistRepository.findByUserId(userId).stream()
                    .map(item -> bookMapper.toDto(item.getBook())) // Use WishlistMapper for mapping
                    .collect(Collectors.toList());
            return ResponseUtil.success(wishlist);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }


    public ResultDto<List<WishlistDto>> getAll() {
        try {
            var wishlists = wishlistRepository.findAll().stream()
                    .map(wishlistMapper::toDto)
                    .toList();
            return ResponseUtil.success(wishlists);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

}
