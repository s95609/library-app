package com.example.library.service;

import com.example.library.config.JwtService;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BookRentService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private JwtService jwtService;

    public List<Book> getAllUserRentedBooks(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getRentedBooks();
    }

    public List<Book> addRentedBookForUser(Integer bookId, String token) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        Long userId = jwtService.extractClaim(token, claims -> claims.get("userId", Long.class));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.getRentedBooks().add(book);
        book.setQuantity(book.getQuantity()-1);

        userRepository.save(user);
        bookRepository.save(book);

        return user.getRentedBooks();
    }

    public void addRentedBookForUsername(Integer bookId, String username) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.getRentedBooks().add(book);
        book.setQuantity(book.getQuantity()-1);

        userRepository.save(user);
        bookRepository.save(book);
    }

    public List<Book> deleteRentedBookFromUser(Integer bookId, String token) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        Long userId = jwtService.extractClaim(token, claims -> claims.get("userId", Long.class));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.getRentedBooks().remove(book);
        book.setQuantity(book.getQuantity()+1);

        userRepository.save(user);
        bookRepository.save(book);

        return user.getRentedBooks();
    }

    public void deleteRentedBookFromUsername(Integer bookId, String username) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.getRentedBooks().remove(book);
        book.setQuantity(book.getQuantity()+1);

        userRepository.save(user);
        bookRepository.save(book);
    }
}
