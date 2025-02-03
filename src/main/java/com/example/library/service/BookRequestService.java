package com.example.library.service;

import com.example.library.dto.BookRequest.CreateBookRequestDto;
import com.example.library.dto.BookRequest.ResponseBookRequestDto;
import com.example.library.mapper.BookRequestMapper;
import com.example.library.repository.BookRequestRepository;
import com.example.library.repository.UserRepository;
import com.example.library.dto.ResultDto;
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
    public ResultDto<ResponseBookRequestDto> create(CreateBookRequestDto createBookRequestDto) {
        try {
            var username = getUsername();
            var existingRequest = bookRequestRepository.findByAuthorAndTitle(
                    createBookRequestDto.getAuthor(), createBookRequestDto.getTitle());
            if (existingRequest.isPresent()) {
                throw new CustomException.BadRequest("Book request already exists for the user and book.");
            }
            var bookRequest = bookRequestMapper.toEntity(createBookRequestDto);
            bookRequest.setUser(userRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomException.NotFound("User not found: " + username)));
            bookRequest.setFulfilled(false);
            bookRequestRepository.save(bookRequest);
            return ResponseUtil.created(bookRequestMapper.toDto(bookRequest));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<List<ResponseBookRequestDto>> getListAdmin() {
        try {
            var bookRequests = bookRequestRepository.findAll().stream()
                    .map(bookRequestMapper::toDto)
                    .toList();
            return ResponseUtil.success(bookRequests);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<List<ResponseBookRequestDto>> getList() {
        try {
            var username = getUsername();
            var bookRequests = bookRequestRepository.findByUsername(username)
                    .stream()
                    .map(bookRequestMapper::toDto)
                    .toList();
            return ResponseUtil.success(bookRequests);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ResultDto<Boolean> delete(Long id) {
        try {
            int deletedRows = bookRequestRepository.deleteByIdAndReturnAffectedRows(id);
            if (deletedRows == 0) {
                throw new CustomException.NotFound("BookRequest not found with id: " + id);
            }
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ResultDto<Boolean> update(Long id, boolean status) {
        try {
            var bookRequest = bookRequestRepository.findById(id)
                    .orElseThrow(() -> new CustomException.NotFound("BookRequest not found with id: " + id));
            bookRequest.setFulfilled(status);
            bookRequestRepository.save(bookRequest);
            return ResponseUtil.updated(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }
}
