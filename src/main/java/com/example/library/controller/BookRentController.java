package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookRentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rent")
@AllArgsConstructor
public class BookRentController {

    private BookRentService bookRentService;

    @GetMapping("/user/{id}")
    public List<Book> getAllUserRentedBooks(@PathVariable Long id) {
        return bookRentService.getAllUserRentedBooks(id);
    }

    @PostMapping
    public List<Book> addRentedBookForUser(@RequestParam Integer bookId, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        return bookRentService.addRentedBookForUser(bookId, token.substring(7));
    }

    @DeleteMapping
    public List<Book> deleteRentedBookForUser(@RequestParam Integer bookId, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        return bookRentService.deleteRentedBookFromUser(bookId, token.substring(7));
    }
}
