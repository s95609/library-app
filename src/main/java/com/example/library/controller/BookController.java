package com.example.library.controller;

import com.example.library.dto.BookRequest;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book createBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @DeleteMapping("{id}")
    public Book deleteBook(@PathVariable Integer id) {
        return bookService.deleteBook(id);
    }
}
