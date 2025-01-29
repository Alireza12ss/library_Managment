package com.example.library.service;

import com.example.library.dto.BookRequestDto;
import com.example.library.mapper.BookRequestMapper;
import com.example.library.repository.BookRequestRepository;
import com.example.library.repository.UserRepository;
import com.example.library.util.ApiResponse;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRequestService extends SuperService {

    private final BookRequestRepository bookRequestRepository;
    private final UserRepository userRepository;
    private final BookRequestMapper bookRequestMapper;

    public BookRequestService(UserRepository userRepository, BookRequestRepository bookRequestRepository,
                              UserRepository userRepository1, BookRequestMapper bookRequestMapper) {
        super(userRepository);
        this.bookRequestRepository = bookRequestRepository;
        this.userRepository = userRepository1;
        this.bookRequestMapper = bookRequestMapper;
    }

    @Transactional
    public ApiResponse<BookRequestDto> createBookRequest(BookRequestDto bookRequestDto) {
        try {
            var username = getUsername();
            var existingRequest = bookRequestRepository.findByAuthorAndTitle(bookRequestDto.getAuthor(), bookRequestDto.getTitle());
            if (existingRequest.isPresent()) {
                throw new CustomException.BadRequest("Book request already exists for the user and book.");
            }
            var bookRequest = bookRequestMapper.toEntity(bookRequestDto); // Use BookRequestMapper for mapping
            bookRequest.setUser(userRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomException.NotFound("User not found: " + username)));
            bookRequest.setFulfilled(false);
            bookRequestRepository.save(bookRequest);
            return ResponseUtil.created(bookRequestDto);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<BookRequestDto>> getAllUserBookRequests() {
        try {
            var bookRequests = bookRequestRepository.findAll().stream()
                    .map(bookRequestMapper::toDto) // Use BookRequestMapper for mapping
                    .collect(Collectors.toList());
            return ResponseUtil.success(bookRequests);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<BookRequestDto>> getAllBookRequests() {
        try {
            var username = getUsername();
            var userId = userRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomException.NotFound("User not found: " + username))
                    .getId();
            var bookRequests = bookRequestRepository.findByUserId(userId).stream()
                    .map(bookRequestMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseUtil.success(bookRequests);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Boolean> deleteBookRequest(Long id) {
        try {
            if (!bookRequestRepository.existsById(id)) {
                throw new CustomException.NotFound("BookRequest not found with id: " + id);
            }
            bookRequestRepository.deleteById(id);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Boolean> updateRequestStatus(Long id, boolean status) {
        try {
            var bookRequest = bookRequestRepository.findById(id)
                    .orElseThrow(() -> new CustomException.NotFound("BookRequest not found with id: " + id));
            if (status) {
                bookRequest.setFulfilled(true);
                bookRequestRepository.save(bookRequest);
                return ResponseUtil.updated(true);
            }
            return ResponseUtil.updated(false);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }
}
