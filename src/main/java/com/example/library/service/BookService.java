package com.example.library.service;

import com.example.library.dto.Book.CreateUpdateBookDto;
import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookGroupRepository;
import com.example.library.repository.BookRepository;
import com.example.library.dto.ResultDto;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookGroupRepository bookGroupRepository;
    private final BookMapper bookMapper;

    // Get all books
    public ResultDto<List<ResponseBookDto>> getAll() {
        try {
            List<ResponseBookDto> allBooks = bookRepository.findAll()
                    .stream()
                    .map(bookMapper::toDto).toList();
            return ResponseUtil.success(allBooks);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError("dont.don");
        }
    }

    // Get a single book by ID
    public ResultDto<ResponseBookDto> findById(Long id) {
        try {
            var result = bookRepository.findById(id)
                    .orElseThrow(() -> new CustomException.NotFound("Book not found"));
            return ResponseUtil.success(bookMapper.toDto(result));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }


    public ResultDto<ResponseBookDto> create(CreateUpdateBookDto bookDto) {
        try {
            if (bookRepository.findByTitleAndAuthor(bookDto.getAuthor(), bookDto.getTitle())) {
                throw new CustomException.Conflict("Duplicate Book");
            }
            BookGroup bookGroup = bookGroupRepository.findByName(bookDto.getGroup())
                    .orElseThrow(() -> new CustomException.NotFound("Book group not found"));
            Book book = bookMapper.toEntity(bookDto);
            book.setGroup(bookGroup);
            Book savedBook = bookRepository.save(book);
            return ResponseUtil.success(bookMapper.toDto(savedBook));
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException.ServerError(e.getMessage());
        }
    }

    @Transactional
    public ResultDto<ResponseBookDto> update(Long id, CreateUpdateBookDto bookDto) {
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


    public ResultDto<List<ResponseBookDto>> search(String keyword) {
        try {
            var list = bookRepository.findByTitleContainingIgnoreCase(keyword).stream()
                    .map(bookMapper::toDto)
                    .toList();
            return ResponseUtil.success(list);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }


    @Transactional
    public ResultDto<Boolean> delete(Long id) {
        try {
           var book = bookRepository.findById(id).orElseThrow(
                   () -> new CustomException.NotFound("")
           );
           bookRepository.delete(book);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

}