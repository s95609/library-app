package com.example.library.service;

import com.example.library.dto.BookGenreRequest;
import com.example.library.model.BookGenre;
import com.example.library.repository.BookGenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class BookGenreService {

    private BookGenreRepository bookGenreRepository;

    public List<BookGenre> getAllBookGenres() {
        return bookGenreRepository.findAll();
    }

    public BookGenre createBookGenre(BookGenreRequest bookGenreRequest) {
        if (bookGenreRequest.getName() == null || bookGenreRequest.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genre name cannot be null or empty");
        }
        BookGenre bookGenre = BookGenre.builder()
                .name(bookGenreRequest.getName())
                .build();
        bookGenreRepository.save(bookGenre);
        return bookGenre;
    }

    public BookGenre editBookGenre(Integer id, BookGenreRequest bookGenreRequest) {
        BookGenre bookGenre = bookGenreRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book genre not found"));
        bookGenre.setName(bookGenreRequest.getName());
        bookGenreRepository.save(bookGenre);
        return bookGenre;
    }
}
