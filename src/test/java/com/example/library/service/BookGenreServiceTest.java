package com.example.library.service;

import com.example.library.dto.BookGenreRequest;
import com.example.library.model.BookGenre;
import com.example.library.repository.BookGenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookGenreServiceTest {

    private BookGenreService bookGenreService;

    @Mock
    private BookGenreRepository bookGenreRepository;

    @BeforeEach
    void setUp() {
        bookGenreService = new BookGenreService(bookGenreRepository);
    }

    @Test
    void whenGetAllBookGenres_thenCallFindAll() {
        // when
        bookGenreService.getAllBookGenres();
        // then
        verify(bookGenreRepository).findAll();
    }

    @Test
    void givenBookGenre_whenCreateBookGenre_thenCallSave() {
        // given
        String name = "genre";
        BookGenreRequest bookGenreRequest = new BookGenreRequest(name);
        BookGenre bookGenre = BookGenre.builder().name(name).build();
        // when
        bookGenreService.createBookGenre(bookGenreRequest);
        // then
        ArgumentCaptor<BookGenre> bookGenreArgumentCaptor = ArgumentCaptor.forClass(BookGenre.class);

        verify(bookGenreRepository).save(bookGenreArgumentCaptor.capture());

        BookGenre expected = bookGenreArgumentCaptor.getValue();

        assertEquals(bookGenre, expected);
    }

    @Test
    void givenNoBookGenreName_whenCreateBookGenre_thenThrowException() {
        // given
        String name = "";
        BookGenreRequest bookGenreRequest = new BookGenreRequest(name);
        BookGenre bookGenre = new BookGenre(1, name);
        // when
        // then
        assertThrows(ResponseStatusException.class,
                () -> bookGenreService.createBookGenre(bookGenreRequest), "Genre name cannot be null or empty");
    }

    @Test
    void givenBookGenreName_whenEditBookGenre_thenCallSave() {
        // given
        String newName = "newName";
        String oldName = "oldName";
        BookGenreRequest bookGenreRequest = new BookGenreRequest(newName);
        BookGenre bookGenre = BookGenre.builder().name(oldName).build();
        when(bookGenreRepository.findById(anyInt())).thenReturn(Optional.of(bookGenre));
        BookGenre updatedBookGenre = BookGenre.builder().name(newName).build();
        // when
        bookGenreService.editBookGenre(anyInt(), bookGenreRequest);
        // then
        ArgumentCaptor<BookGenre> bookGenreArgumentCaptor = ArgumentCaptor.forClass(BookGenre.class);

        verify(bookGenreRepository).save(bookGenreArgumentCaptor.capture());

        BookGenre expected = bookGenreArgumentCaptor.getValue();

        assertEquals(updatedBookGenre, expected);
    }

    @Test
    void givenInvalidBookGenreName_whenEditBookGenre_thenThrowException() {
        // given
        String newName = "newName";
        BookGenreRequest bookGenreRequest = new BookGenreRequest(newName);
        BookGenre updatedBookGenre = BookGenre.builder().name(newName).build();
        // when
        // then
        assertThrows(ResponseStatusException.class,
                () -> bookGenreService.editBookGenre(1, bookGenreRequest), "Book genre not found");
    }
}