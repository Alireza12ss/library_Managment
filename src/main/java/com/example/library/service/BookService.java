package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookGroupRepository;
import com.example.library.repository.BookRepository;
import com.example.library.util.ApiResponse;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookGroupRepository bookGroupRepository;
    private final BookMapper bookMapper;

    // Get all books
    public ApiResponse<List<BookDto>> getAllBooks() {
        try {
            List<BookDto> allBooks = bookRepository.findAll()
                    .stream()
                    .map(bookMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseUtil.success(allBooks);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    // Get a single book by ID
    public ApiResponse<BookDto> getBookById(Long id) {
        try {
            var result = bookRepository.findById(id).map(bookMapper::toDto)
                    .orElseThrow(() -> new CustomException.NotFound("Book not found"));
            return ResponseUtil.success(result);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }


    public ApiResponse<BookDto> addBook(BookDto bookDto) {
        try {
            if (bookRepository.findByTitleAndAuthor(bookDto.getAuthor(), bookDto.getTitle())) {
                throw new CustomException.Conflict("Duplicate Book");
            }
            BookGroup bookGroup = bookGroupRepository.findByName(bookDto.getGroup())
                    .orElseThrow(() -> new CustomException.NotFound("Book group not found"));
            Book book = bookMapper.toEntity(bookDto);
            book.setGroup(bookGroup);
            Book savedBook = bookRepository.save(book);
            return ResponseUtil.created(bookMapper.toDto(savedBook));
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException.ServerError(e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<BookDto> updateBook(Long id, BookDto bookDto) {
        try {
            var existingBook = bookRepository.findById(id)
                    .orElseThrow(() -> new CustomException.NotFound("Book not found with ID: " + id));
            var updatedBook = bookMapper.partialUpdate(bookDto, existingBook);
            if (bookDto.getGroup() != null) {
                var group = bookGroupRepository.findByName(bookDto.getGroup())
                        .orElseThrow(() -> new CustomException.NotFound("Book group not found with name: " + bookDto.getGroup()));
                updatedBook.setGroup(group);
            }
            var savedBook = bookRepository.save(updatedBook);
            return ResponseUtil.updated(bookMapper.toDto(savedBook));
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException.ServerError(e.getMessage());
        }
    }


    public ApiResponse<List<BookDto>> searchBooks(String keyword) {
        try {
            var list = bookRepository.searchByKeyword(keyword).stream()
                    .map(bookMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseUtil.success(list);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }


    @Transactional
    public ApiResponse<Boolean> deleteBook(Long id) {
        try {
            if (!bookRepository.existsById(id)) {
                throw new CustomException.NotFound("Book not found with ID: " + id);
            }
            bookRepository.deleteById(id);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

}