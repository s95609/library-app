package com.example.library.controller;

import com.example.library.dto.BookGenreRequest;
import com.example.library.model.BookGenre;
import com.example.library.service.BookGenreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@AllArgsConstructor
public class BookGenreController {

    private BookGenreService bookGenreService;

    @GetMapping
    public List<BookGenre> getAllBookGenres() {
        return bookGenreService.getAllBookGenres();
    }

    @PostMapping
    public BookGenre createBookGenre(@RequestBody BookGenreRequest bookGenreRequest) {
        return bookGenreService.createBookGenre(bookGenreRequest);
    }

    @PutMapping("{id}")
    public BookGenre editBookGenre(@PathVariable Integer id, @RequestBody BookGenreRequest bookGenreRequest) {
        return bookGenreService.editBookGenre(id, bookGenreRequest);
    }

}
