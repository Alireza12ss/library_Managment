package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.WishlistDto;
import com.example.library.entity.Book;
import com.example.library.entity.User;
import com.example.library.entity.Wishlist;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.UserNotFoundException;
import com.example.library.exception.WishlistItemNotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService extends SuperService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    // Constructor with only one UserRepository parameter
    public WishlistService(UserRepository userRepository, WishlistRepository wishlistRepository,
                           BookRepository bookRepository) {
        super(userRepository);
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    // Add book to the user's wishlist
    public void addBookToWishlist(Long bookId) {
        Long userId = getCurrentUserId();

        // Check if the book is already in the wishlist
        if (wishlistRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new WishlistItemNotFoundException("Book already in wishlist");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setBook(book);

        wishlistRepository.save(wishlist);
    }

    // Remove book from the user's wishlist
    @Transactional
    public void removeBookFromWishlist(Long bookId) {
        Long userId = getCurrentUserId();

        // Check if the book is in the wishlist
        if (!wishlistRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new WishlistItemNotFoundException("Book not in wishlist");
        }

        wishlistRepository.deleteByUserIdAndBookId(userId, bookId);
    }

    // Get the current user's wishlist
    public List<BookDto> getUserWishlist() {
        Long userId = getCurrentUserId();

        // Get books from the wishlist
        return wishlistRepository.findByUserId(userId).stream()
                .map(wishlist -> BookMapper.mapToBookDto(wishlist.getBook()))
                .collect(Collectors.toList());
    }

    public List<BookDto> getUserWishlist(Long userId) {

        // Get books from the wishlist
        return wishlistRepository.findByUserId(userId).stream()
                .map(wishlist -> BookMapper.mapToBookDto(wishlist.getBook()))
                .collect(Collectors.toList());
    }

    // Get all wishlists (for admin)
    public List<WishlistDto> getAllWishlists() {
        return wishlistRepository.findAll().stream()
                .map(this::mapToWishlistDto)
                .collect(Collectors.toList());
    }

    // Delete wishlist item by wishlistId
    public void deleteWishlistItem(Long wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }

    // Map Wishlist to WishlistDto for admin view
    private WishlistDto mapToWishlistDto(Wishlist wishlist) {
        WishlistDto dto = new WishlistDto();
        dto.setUser(wishlist.getUser().getUsername());
        dto.setBook(wishlist.getBook().getTitle());
        return dto;
    }
}
