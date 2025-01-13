package com.example.library.service;

import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookRequestResponse;
import com.example.library.entity.BookRequest;
import com.example.library.exception.UserNotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRequestRepository;
import com.example.library.repository.UserRepository;
import com.example.library.exception.BookRequestNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRequestService extends SuperService {

    private final BookRequestRepository bookRequestRepository;
    private final UserRepository userRepository;

    public BookRequestService(UserRepository userRepository, BookRequestRepository bookRequestRepository, UserRepository userRepository1) {
        super(userRepository);
        this.bookRequestRepository = bookRequestRepository;
        this.userRepository = userRepository1;
    }

    public BookRequestResponse createBookRequest(BookRequestDto bookRequestDto) {
        String username = getUsername();
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle(bookRequestDto.getTitle());
        bookRequest.setAuthor(bookRequestDto.getAuthor());
        // If the user is not found, throw custom exception
        bookRequest.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username)));
        bookRequest.setFulfilled(false);
        bookRequestRepository.save(bookRequest);

        return new BookRequestResponse(
                bookRequest.getTitle(),
                bookRequest.getAuthor()
        );
    }

    public List<BookRequestDto> getAllBookRequests() {
        String username = getUsername();
        // If the user is not found, throw custom exception
        return bookRequestRepository.findById(
                        userRepository.findByUsername(username)
                                .orElseThrow(() -> new UserNotFoundException("User not found: " + username))
                                .getId()
                ).stream()
                .map(BookMapper::mapToBookRequestDto)
                .collect(Collectors.toList());
    }

    public void deleteBookRequest(Long id) {
        // If the book request is not found, throw custom exception
        if (!bookRequestRepository.existsById(id)) {
            throw new BookRequestNotFoundException("BookRequest not found with id: " + id);
        }
        bookRequestRepository.deleteById(id);
    }

    public void updateRequestStatus(Long id, String status) {
        BookRequest bookRequest = bookRequestRepository.findById(id)
                .orElseThrow(() -> new BookRequestNotFoundException("BookRequest not found with id: " + id));

        if (status.equals("FULFILLED")) {
            bookRequest.setFulfilled(true);
            bookRequestRepository.save(bookRequest);
        }
    }
}
