package com.example.library.repository;

import com.example.library.model.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, Integer> {
    Optional<BookGenre> findByName(String name);
}
