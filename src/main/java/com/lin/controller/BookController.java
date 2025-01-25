package com.lin.controller;

import com.lin.entity.Book;
import com.lin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Get all books
    @GetMapping(value = "/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Get a book by its ID
    @GetMapping(value = "/{id}")
    public Optional<Book> getBookById(@PathVariable("id") String bookId) {
        return bookService.getBookById(bookId);
    }

    // Add or update a book
    @PostMapping(value = "/save")
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    // Delete a book
    @DeleteMapping(value = "/delete/{id}")
    public void deleteBook(@PathVariable("id") String bookId) {
        bookService.deleteBook(bookId);
    }

    // Get books suitable for a certain age range
    @GetMapping(value = "/age-range")
    public List<Book> getBooksByAgeRange(@RequestParam("ageRange") String ageRange) {
        return bookService.getBooksByAgeRange(ageRange);
    }
}
