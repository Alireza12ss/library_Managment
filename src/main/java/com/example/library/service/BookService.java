package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import com.example.library.repository.BookGroupRepository;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookGroupRepository bookGroupRepository;

    public BookService(BookRepository bookRepository, BookGroupRepository bookGroupRepository) {
        this.bookRepository = bookRepository;
        this.bookGroupRepository = bookGroupRepository;
    }

    // Get all books
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Get a single book by ID
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
        return mapToDto(book);
    }

    // Add a new book
    public BookDto addBook(BookDto bookDto) {
        Book book = mapToEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return mapToDto(savedBook);
    }

    // Update an existing book
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setPrice(bookDto.getPrice());

        if (bookDto.getGroup() != null) {
            BookGroup group = bookGroupRepository.findByName(bookDto.getGroup())
                    .orElseThrow(() -> new RuntimeException("Book group not found with name: " + bookDto.getGroup()));
            existingBook.setGroup(group);
        }

        Book updatedBook = bookRepository.save(existingBook);
        return mapToDto(updatedBook);
    }

    public List<BookDto> searchBooks(String keyword) {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }
    // Helper method to map Book to BookDto
    private BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getTitle(),
                book.getAuthor(),
                book.getGroup() != null ? book.getGroup().getName() : null,
                book.getPrice()
        );
    }

    // Delete a book by ID
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    // Convert Book entity to BookDto
    private BookDto mapToDto(Book book) {
        return new BookDto(
                book.getTitle(),
                book.getAuthor(),
                book.getGroup().getName(),
                book.getPrice()
        );
    }

    // Convert BookDto to Book entity
    private Book mapToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());

        if (bookDto.getGroup() != null) {
            BookGroup group = bookGroupRepository.findByName(bookDto.getGroup())
                    .orElseThrow(() -> new RuntimeException("Book group not found with name: " + bookDto.getGroup()));
            book.setGroup(group);
        }

        return book;
    }
}
