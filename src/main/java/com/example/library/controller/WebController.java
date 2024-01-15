package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.BookRentService;
import com.example.library.service.BookService;
import com.example.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class WebController {

    private BookService bookService;
    private BookRentService bookRentService;
    private UserService userService;

    @GetMapping("/")
    public String displayHomePage(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/rent")
    public String displayRentPage(@RequestParam Integer bookId, Model model) {
        model.addAttribute("book", bookService.getBookById(bookId));
        return "rent";
    }

    @PostMapping("/rent")
    public String rentSubmit(@ModelAttribute Book book) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        bookRentService.addRentedBookForUsername(book.getId(), username);
        return "result";
    }

    @GetMapping("/profile")
    public String displayProfilePage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userService.loadUserByUsername(username);

        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/return")
    public String returnBook(@RequestParam Integer bookId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        bookRentService.deleteRentedBookFromUsername(bookId, username);

        User user = (User) userService.loadUserByUsername(username);
        model.addAttribute("user", user);

        return "redirect:/profile";
    }
}
