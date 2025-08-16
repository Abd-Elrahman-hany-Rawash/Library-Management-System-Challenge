package com.library.Library.Management.System.Challenge.service;

import com.library.Library.Management.System.Challenge.ResourceNotFoundException;
import com.library.Library.Management.System.Challenge.entity.Book;
import com.library.Library.Management.System.Challenge.entity.SystemUser;
import com.library.Library.Management.System.Challenge.entity.UserActivityLog;
import com.library.Library.Management.System.Challenge.repo.BookRepository;
import com.library.Library.Management.System.Challenge.repo.UserActivityLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserActivityLogRepository logRepository;

    public BookService(BookRepository bookRepository, UserActivityLogRepository logRepository) {
        this.bookRepository = bookRepository;
        this.logRepository = logRepository;
    }

    //  get currently logged-in user
    private SystemUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof SystemUser user) {
            return user;
        }
        return null;
    }

    @Transactional
    public Book saveBook(Book book) {
        Book saved = bookRepository.save(book);
        logAction("CREATE_BOOK", "Created book: " + saved.getTitle());
        return saved;
    }

    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));

        if (updatedBook.getTitle() != null) book.setTitle(updatedBook.getTitle());
        if (updatedBook.getIsbn() != null) book.setIsbn(updatedBook.getIsbn());
        if (updatedBook.getEdition() != null) book.setEdition(updatedBook.getEdition());
        if (updatedBook.getPublicationYear() != 0) book.setPublicationYear(updatedBook.getPublicationYear());
        book.setAvailable(updatedBook.isAvailable());

        Book saved = bookRepository.save(book);
        logAction("UPDATE_BOOK", "Updated book: " + saved.getTitle());
        return saved;
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
        bookRepository.delete(book);
        logAction("DELETE_BOOK", "Deleted book: " + book.getTitle());
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }

    //  uses the current logged-in user
    private void logAction(String action, String details) {
        SystemUser currentUser = getCurrentUser();
        if (currentUser != null) {
            UserActivityLog log = new UserActivityLog();
            log.setUser(currentUser);
            log.setAction(action);
            log.setDetails(details);
            logRepository.save(log);
        }
    }
}
