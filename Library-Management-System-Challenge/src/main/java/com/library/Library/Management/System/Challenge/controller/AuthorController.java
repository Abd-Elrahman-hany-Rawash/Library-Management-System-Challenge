package com.library.Library.Management.System.Challenge.controller;

import com.library.Library.Management.System.Challenge.entity.Author;
import com.library.Library.Management.System.Challenge.service.AuthorService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Create Author
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.saveAuthor(author));
    }

    // Get All Authors
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STAFF','LIBRARIAN')")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    // Get Author by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STAFF','LIBRARIAN')")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    // Update Author
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR','LIBRARIAN')")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable Long id,
            @RequestBody Author updatedAuthor
    ) {
        return ResponseEntity.ok(authorService.updateAuthor(id, updatedAuthor));
    }

    // Delete Author
    @DeleteMapping("/{id}")
     @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) throws BadRequestException {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
