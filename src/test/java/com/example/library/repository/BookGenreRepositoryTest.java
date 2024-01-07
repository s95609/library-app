package com.example.library.repository;

import com.example.library.model.BookGenre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookGenreRepositoryTest {

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @AfterEach
    void tearDown() {
        bookGenreRepository.deleteAll();
    }

    @Test
    void givenValidName_whenFindByName_thenReturnBookGenreOptional() {
        // given
        String name = "validName";
        BookGenre bookGenre = BookGenre.builder()
                .id(1)
                .name(name)
                .build();
        bookGenreRepository.save(bookGenre);
        // when
        Optional<BookGenre> expected = bookGenreRepository.findByName(name);
        // then
        assertEquals(Optional.of(bookGenre), expected);
    }

    @Test
    void givenInvalidName_whenFindByName_thenReturnEmptyOptional() {
        // given
        String name = "validName";
        // when
        Optional<BookGenre> expected = bookGenreRepository.findByName(name);
        // then
        assertEquals(Optional.empty(), expected);
    }
}