package com.example.library.service;

import com.example.library.dto.BookGroupDto;
import com.example.library.dto.BookGroupReqDto;
import com.example.library.entity.BookGroup;
import com.example.library.exception.BookGroupNotFoundException;
import com.example.library.exception.DuplicateBookGroupException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.library.mapper.BookMapper.mapToBookGroupDto;

@Service
public class BookGroupService {

    private final BookGroupRepository bookGroupRepository;

    public BookGroupService(BookGroupRepository bookGroupRepository) {
        this.bookGroupRepository = bookGroupRepository;
    }

    public List<BookGroupDto> searchBookGroups(String keyword) {
        List<BookGroup> bookGroups = bookGroupRepository.findAll();

        return bookGroups.stream()
                .filter(group -> group.getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(BookMapper::mapToBookGroupDto) // Map to BookGroupDto
                .collect(Collectors.toList());
    }

    // Create a new Book Group
    public BookGroupDto createBookGroup(BookGroupReqDto bookGroupDto) {
        Optional<BookGroup> existingGroup = bookGroupRepository.findByName(bookGroupDto.getName());
        if (existingGroup.isPresent()) {
            throw new DuplicateBookGroupException("Book group with the name '" + bookGroupDto.getName() + "' already exists.");
        }

        BookGroup bookGroup = new BookGroup();
        bookGroup.setName(bookGroupDto.getName());
        return mapToBookGroupDto(bookGroupRepository.save(bookGroup));
    }

    public List<BookGroupDto> getAllBookGroups() {
        return bookGroupRepository.findAll().stream()
                .map(BookMapper::mapToBookGroupDto) // Map each BookGroup to BookGroupDto
                .collect(Collectors.toList());
    }

    // Update an existing Book Group
    public BookGroupDto updateBookGroup(Long id, BookGroupDto bookGroupDto) {
        BookGroup bookGroup = bookGroupRepository.findById(id)
                .orElseThrow(() -> new BookGroupNotFoundException("Book group with ID " + id + " not found."));

        bookGroup.setName(bookGroupDto.getName());
        return mapToBookGroupDto(bookGroupRepository.save(bookGroup));
    }

    // Delete a Book Group
    public void deleteBookGroup(Long id) {
        if (!bookGroupRepository.existsById(id)) {
            throw new BookGroupNotFoundException("Book group with ID " + id + " not found.");
        }

        bookGroupRepository.deleteById(id);
    }
}
