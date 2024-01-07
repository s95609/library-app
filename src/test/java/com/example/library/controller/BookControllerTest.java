package com.example.library.controller;

import com.example.library.dto.BookRequest;
import com.example.library.model.Book;
import com.example.library.model.BookGenre;
import com.example.library.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private BookRepository bookRepository;

    @Test
    void whenGetAllBooks_thenStatus200() throws Exception {
        // when
        ResultActions resultActions = mvc
                .perform(get("/api/book"));
        // then
        resultActions.andExpect(status().isOk()).andReturn();
    }

    @Test
    void givenBook_whenCreateBook_thenStatus200() throws Exception {
        // given
        BookGenre bookGenre = new BookGenre(1, "genre");
        BookRequest bookRequest = new BookRequest("book1", 10, bookGenre.getName());
        Book book = new Book(1, bookRequest.getName(), bookRequest.getQuantity(), bookGenre);
        // when
        ResultActions resultActions = mvc
                .perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)));
        // then
        resultActions.andExpect(status().isOk());
        List<Book> books = bookRepository.findAll();
        assertEquals(books.get(0), book);
    }
}