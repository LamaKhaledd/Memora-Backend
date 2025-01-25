package com.lin.service;

import com.lin.entity.Book;
import com.lin.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Create or update a book
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get a book by its ID
    public Optional<Book> getBookById(String bookId) {
        return bookRepository.findById(bookId);
    }

    // Delete a book
    public void deleteBook(String bookId) {
        bookRepository.deleteById(bookId);
    }

    // Get books by age range
    public List<Book> getBooksByAgeRange(String ageRange) {
        return bookRepository.findAll(); // You can create a query for filtering by age range if needed
    }
}
