package com.example.library.service;

import com.example.library.dto.BookGroup.ResponseBookGroupDto;
import com.example.library.dto.BookGroup.CreateUpdateBookGroupDto;
import com.example.library.mapper.BookGroupMapper;
import com.example.library.repository.BookGroupRepository;
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
public class BookGroupService {

    private final BookGroupRepository bookGroupRepository;
    private final BookGroupMapper bookGroupMapper;

    public ResultDto<List<ResponseBookGroupDto>> search(String keyword) {
        try {
            var filteredGroups = bookGroupRepository.findBookGroupByNameContainingIgnoreCase(keyword).stream()
                    .map(bookGroupMapper::toDto)
                    .toList();
            return ResponseUtil.success(filteredGroups);
        } catch (Exception e) {
            throw new CustomException.ServerError("Error while searching for book groups: " + e.getMessage());
        }
    }

    @Transactional
    public ResultDto<ResponseBookGroupDto> create(CreateUpdateBookGroupDto bookGroupReqDto) {
        try {
            if (bookGroupRepository.existsByName(bookGroupReqDto.getName())) {
                throw new CustomException.Conflict(
                        "Book group with the name '" +
                                bookGroupReqDto.getName() +
                                "' already exists."
                );
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

    public ResultDto<List<ResponseBookGroupDto>> getAll() {
        try {
            var bookGroups = bookGroupRepository.findAll().stream()
                    .map(bookGroupMapper::toDto)
                    .toList();
            return ResponseUtil.success(bookGroups);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ResultDto<ResponseBookGroupDto> update(Long id, CreateUpdateBookGroupDto bookGroupReqDto) {
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
    public ResultDto<Boolean> delete(Long id) {
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
