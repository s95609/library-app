package com.example.library.service;

import com.example.library.dto.BookRequest;
import com.example.library.model.Book;
import com.example.library.model.BookGenre;
import com.example.library.repository.BookGenreRepository;
import com.example.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private BookGenreRepository bookGenreRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(BookRequest bookRequest) {
        BookGenre bookGenre = bookGenreRepository.findByName(bookRequest.getGenre())
                .orElse(BookGenre.builder().name(bookRequest.getGenre()).build());
        Book book = Book.builder()
                .name(bookRequest.getName())
                .quantity(bookRequest.getQuantity())
                .genre(bookGenre)
                .build();
        bookRepository.save(book);
        return book;
    }

    public Book deleteBook(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        bookRepository.delete(book);
        return book;
    }

    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }
}
