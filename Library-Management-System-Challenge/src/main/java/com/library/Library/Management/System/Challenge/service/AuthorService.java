package com.library.Library.Management.System.Challenge.service;

import com.library.Library.Management.System.Challenge.ResourceNotFoundException;
import com.library.Library.Management.System.Challenge.entity.Author;
import com.library.Library.Management.System.Challenge.repo.AuthorRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Create
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Read all
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Read by ID
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with ID " + id + " not found"));
    }

    // Update
    @Transactional
    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author author = getAuthorById(id);
        if (updatedAuthor.getName() != null && !updatedAuthor.getName().isBlank()) {
            author.setName(updatedAuthor.getName());
        }
        return authorRepository.save(author);
    }

    @Transactional
    public boolean deleteAuthor(Long id) throws BadRequestException {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author with ID " + id + " not found");
        }

        long assignedBooks = authorRepository.countByAuthorId(id);
        if (assignedBooks > 0) {
            throw new BadRequestException( // Custom Exception
                    "Cannot delete author because they are assigned to " + assignedBooks + " book(s)."
            );
        }

        authorRepository.deleteById(id);
        return true;
    }


}
