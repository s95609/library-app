package com.example.library.dto;

import com.example.library.model.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String name;
    private Integer quantity;
    private String genre;
}
