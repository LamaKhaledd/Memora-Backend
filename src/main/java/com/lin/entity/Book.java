package com.lin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String bookId; 
    private String userId; 
    private String title;
    private String author;
    private String imageUrl; 
    private String ageRange; // Age suitability for the book
    private String downloadLink; // Link to download the book
    private String description; // Short description of the book
}
