package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookGroupDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import com.example.library.repository.BookGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .map(this::mapToBookGroupDto) // Map to BookGroupDto
                .collect(Collectors.toList());
    }

    // Helper method to map BookGroup to BookGroupDto
    private BookGroupDto mapToBookGroupDto(BookGroup bookGroup) {
        BookGroupDto dto = new BookGroupDto();
        dto.setName(bookGroup.getName());
        dto.setBooks(bookGroup.getBooks().stream()
                .map(this::mapToBookDto) // Map each Book to BookDto
                .collect(Collectors.toList()));
        return dto;
    }

    // Helper method to map Book to BookDto
    private BookDto mapToBookDto(Book book) {
        BookDto dto = new BookDto(
                book.getTitle(),
                book.getAuthor(),
                book.getGroup().getName(),
                book.getPrice()
        );
        // Map other fields as necessary
        return dto;
    }



    // Create a new Book Group
    public BookGroup createBookGroup(BookGroupDto bookGroupDto) {
        Optional<BookGroup> existingGroup = bookGroupRepository.findByName(bookGroupDto.getName());
        if (existingGroup.isPresent()) {
            throw new IllegalArgumentException("Book group with the same name already exists");
        }

        BookGroup bookGroup = new BookGroup();
        bookGroup.setName(bookGroupDto.getName());

        return bookGroupRepository.save(bookGroup);
    }

    // Get all Book Groups
    public List<BookGroup> getAllBookGroups() {
        return bookGroupRepository.findAll();
    }

    // Update an existing Book Group
    public BookGroup updateBookGroup(Long id, BookGroupDto bookGroupDto) {
        BookGroup bookGroup = bookGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book group not found"));

        bookGroup.setName(bookGroupDto.getName());

        return bookGroupRepository.save(bookGroup);
    }

    // Delete a Book Group
    public void deleteBookGroup(Long id) {
        if (!bookGroupRepository.existsById(id)) {
            throw new IllegalArgumentException("Book group not found");
        }

        bookGroupRepository.deleteById(id);
    }
}
