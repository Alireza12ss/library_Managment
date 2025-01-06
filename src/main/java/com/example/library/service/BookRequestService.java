package com.example.library.service;

import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookRequestResponse;
import com.example.library.entity.BookRequest;
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

    public BookRequestResponse createBookRequest(BookRequestDto bookRequestDto, String username) {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle(bookRequestDto.getTitle());
        bookRequest.setAuthor(bookRequestDto.getAuthor());
        bookRequest.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username)));
        bookRequest.setFulfilled(false);

        bookRequestRepository.save(bookRequest);
        BookRequestResponse bookRequestResponse = new BookRequestResponse(
                bookRequest.getTitle(),
                bookRequest.getAuthor()
        );
        return bookRequestResponse;
    }

    private BookRequestDto mapToBookRequestDto(BookRequest bookRequest) {
        BookRequestDto dto = new BookRequestDto();
        dto.setTitle(bookRequest.getTitle());
        dto.setAuthor(bookRequest.getAuthor());
        dto.setFulfilled(bookRequest.isFulfilled());
        return dto;
    }



    public List<BookRequestDto> getAllBookRequests(String username) {
        return bookRequestRepository.findById(
                userRepository.findByUsername(username).get().getId()
                ).stream()
                .map(this::mapToBookRequestDto)
                .collect(Collectors.toList());
    }


    public void deleteBookRequest(Long id) {
        bookRequestRepository.deleteById(id);
    }


    public void updateRequestStatus(Long id, String status) {
        BookRequest bookRequest = bookRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookRequest not found with id: " + id));
        if (status.equals("FULFILLED")) {
            bookRequest.setFulfilled(true);
            bookRequestRepository.save(bookRequest);
        }
    }
}
