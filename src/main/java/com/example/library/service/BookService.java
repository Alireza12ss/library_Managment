package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookGroupRepository;
import com.example.library.repository.BookRepository;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.BookGroupNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookGroupRepository bookGroupRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookGroupRepository bookGroupRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookGroupRepository = bookGroupRepository;
        this.bookMapper = bookMapper;
    }

    // Get all books
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    // Get a single book by ID
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
        return BookMapper.mapToBookDto(book);
    }

    public BookDto addBook(BookDto bookDto) {
        BookGroup bookGroup = bookGroupRepository.findByName(bookDto.getGroup())
                .orElseThrow(() -> new BookGroupNotFoundException("Book group not found"));
        Book book = BookMapper.mapToBook(bookDto);
        book.setGroup(bookGroup);
        Book savedBook = bookRepository.save(book);
        return BookMapper.mapToBookDto(savedBook);
    }

    // Update an existing book
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setPrice(bookDto.getPrice());

        if (bookDto.getGroup() != null) {
            BookGroup group = bookGroupRepository.findByName(bookDto.getGroup())
                    .orElseThrow(() -> new BookGroupNotFoundException("Book group not found with name: " + bookDto.getGroup()));
            existingBook.setGroup(group);
        }

        Book updatedBook = bookRepository.save(existingBook);
        return BookMapper.mapToBookDto(updatedBook);
    }

    public List<BookDto> searchBooks(String keyword) {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    // Delete a book by ID
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}
