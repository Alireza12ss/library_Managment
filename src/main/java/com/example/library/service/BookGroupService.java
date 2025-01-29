package com.example.library.service;

import com.example.library.dto.BookGroupDto;
import com.example.library.dto.BookGroupReqDto;
import com.example.library.mapper.BookGroupMapper;
import com.example.library.repository.BookGroupRepository;
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
public class BookGroupService {

    private final BookGroupRepository bookGroupRepository;
    private final BookGroupMapper bookGroupMapper;

    public ApiResponse<List<BookGroupDto>> searchBookGroups(String keyword) {
        try {
            var filteredGroups = bookGroupRepository.searchByKeyword(keyword).stream()
                    .map(bookGroupMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseUtil.success(filteredGroups);
        } catch (Exception e) {
            throw new CustomException.ServerError("Error while searching for book groups: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<BookGroupDto> createBookGroup(BookGroupReqDto bookGroupReqDto) {
        try {
            if (bookGroupRepository.existsByName(bookGroupReqDto.getName())) {
                throw new CustomException.Conflict("Book group with the name '" + bookGroupReqDto.getName() + "' already exists.");
            }
            var bookGroup = bookGroupMapper.toEntity(bookGroupReqDto);
            var savedBookGroup = bookGroupRepository.save(bookGroup);
            return ResponseUtil.success(bookGroupMapper.toDto(savedBookGroup));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ApiResponse<List<BookGroupDto>> getAllBookGroups() {
        try {
            var bookGroups = bookGroupRepository.findAll().stream()
                    .map(bookGroupMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseUtil.success(bookGroups);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ApiResponse<BookGroupDto> updateBookGroup(Long id, BookGroupReqDto bookGroupReqDto) {
        try {
            var bookGroup = bookGroupRepository.findById(id)
                    .orElseThrow(() -> new CustomException.NotFound("Book group with ID " + id + " not found."));
            var bookgroup = bookGroupMapper.partialUpdate(bookGroupReqDto, bookGroup);
            var updatedBookGroup = bookGroupRepository.save(bookgroup);
            return ResponseUtil.success(bookGroupMapper.toDto(updatedBookGroup));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Boolean> deleteBookGroup(Long id) {
        try {
            if (!bookGroupRepository.existsById(id)) {
                throw new CustomException.NotFound("Book group with ID " + id + " not found.");
            }
            bookGroupRepository.deleteById(id);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }
}
