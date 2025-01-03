package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.entity.User;
import com.example.library.entity.Wishlist;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void addBookToWishlist(Long userId, Long bookId) {
        if (wishlistRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new IllegalArgumentException("Book already in wishlist");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setBook(book);
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeBookFromWishlist(Long userId, Long bookId) {
        if (!wishlistRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new IllegalArgumentException("Book not in wishlist");
        }
        wishlistRepository.deleteByUserIdAndBookId(userId, bookId);
    }

    public List<BookDto> getUserWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId).stream()
                .map(wishlist -> mapToBookDto(wishlist.getBook()))
                .collect(Collectors.toList());
    }

    private BookDto mapToBookDto(Book book) {
        BookDto dto = new BookDto(
                book.getTitle(),
                book.getAuthor(),
                book.getGroup().getName(),
                book.getPrice()
        );
        return dto;
    }
}
