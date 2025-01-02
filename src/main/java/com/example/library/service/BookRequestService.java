package com.example.library.service;

import com.example.library.dto.BookRequestDto;
import com.example.library.entity.BookRequest;
import com.example.library.entity.User;
import com.example.library.repository.BookRequestRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRequestService {

    @Autowired
    private BookRequestRepository bookRequestRepository;
    @Autowired
    private UserRepository userRepository;

    public BookRequestDto createBookRequest(BookRequestDto bookRequestDto, String username) {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle(bookRequestDto.getTitle());
        bookRequest.setAuthor(bookRequestDto.getAuthor());
        bookRequest.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username)));
        bookRequest.setFulfilled(false);

        BookRequest savedRequest = bookRequestRepository.save(bookRequest);
        return mapToBookRequestDto(savedRequest); // Assuming ModelMapper is used
    }
    private BookRequestDto mapToBookRequestDto(BookRequest bookRequest) {
        BookRequestDto dto = new BookRequestDto();
        dto.setTitle(bookRequest.getTitle());
        dto.setAuthor(bookRequest.getAuthor());
        dto.setUsername(bookRequest.getUser().getUsername());
        dto.setFulfilled(bookRequest.isFulfilled());
        return dto;
    }



    public List<BookRequestDto> getAllBookRequests() {
        return bookRequestRepository.findAll().stream()
                .map(this::mapToBookRequestDto)
                .collect(Collectors.toList());
    }


    public void deleteBookRequest(Long id) {
        bookRequestRepository.deleteById(id);
    }

    public void markRequestAsFulfilled(Long id) {
        BookRequest bookRequest = bookRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookRequest not found with id: " + id));
        bookRequest.setFulfilled(true);
        bookRequestRepository.save(bookRequest);
    }
}
