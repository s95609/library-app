package com.example.library.controller;

import com.example.library.dto.UserRequest;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.BookRentService;
import com.example.library.service.BookService;
import com.example.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class WebController {

    private BookService bookService;
    private BookRentService bookRentService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String displayHomePage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userService.loadUserByUsername(username);

        model.addAttribute("user", user);
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

    @GetMapping("/delete")
    public String deleteBook(@RequestParam Integer bookId, Model model) {
        bookService.deleteBook(bookId);

        model.addAttribute("books", bookService.getAllBooks());
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String displayEditView(@RequestParam Integer bookId, Model model) {
        model.addAttribute("book", bookService.getBookById(bookId));
        return "edit";
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute Book book, Model model) {
        bookService.editBook(book);

        model.addAttribute("books", bookService.getAllBooks());
        return "redirect:/";
    }

    @GetMapping("/loginpage")
    public String displayLoginView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
        return "redirect:/";
    }

    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "loginPage";
    }

//    @PostMapping("/login")
//    public String login(@RequestBody UserRequest userRequest) {
//        return userService.authenticateUser(userRequest);
//    }

    @GetMapping("/register")
    public ModelAndView displayRegisterView() {
        ModelAndView mv = new ModelAndView("registerPage");
        mv.addObject("user", new User());
        return mv;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        UserRequest userRequest = new UserRequest(user.getUsername(), user.getPassword(), null);
        userService.registerUser(userRequest);
        return "redirect:/login";
    }
}
