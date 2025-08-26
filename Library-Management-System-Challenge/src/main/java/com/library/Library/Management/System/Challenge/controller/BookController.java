package com.library.Library.Management.System.Challenge.controller;

import com.library.Library.Management.System.Challenge.entity.Book;
import com.library.Library.Management.System.Challenge.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //  Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.getBookById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of("error", "Book with ID " + id + " not found")
            );
        }
    }

    //  Create book
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            Book saved = bookService.saveBook(book);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Failed to create book", "details", e.getMessage())
            );
        }
    }

    // Update book
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','LIBRARIAN')")
    public ResponseEntity<?> updateBook(@PathVariable Long id,
                                        @RequestBody Book updatedBook) {
        try {
            Book updated = bookService.updateBook(id, updatedBook);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of("error", "Book with ID " + id + " not found")
            );
        }
    }

    //  Delete book
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of("error", "Book with ID " + id + " not found")
            );
        }
    }
}
