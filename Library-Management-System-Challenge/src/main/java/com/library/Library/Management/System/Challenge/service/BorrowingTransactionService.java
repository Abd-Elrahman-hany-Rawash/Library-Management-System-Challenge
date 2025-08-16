package com.library.Library.Management.System.Challenge.service;

import com.library.Library.Management.System.Challenge.ResourceNotFoundException;
import com.library.Library.Management.System.Challenge.entity.Book;
import com.library.Library.Management.System.Challenge.entity.BorrowingTransaction;
import com.library.Library.Management.System.Challenge.entity.Member;
import com.library.Library.Management.System.Challenge.entity.SystemUser;
import com.library.Library.Management.System.Challenge.repo.BookRepository;
import com.library.Library.Management.System.Challenge.repo.BorrowingTransactionRepository;
import com.library.Library.Management.System.Challenge.repo.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingTransactionService {

    private final BorrowingTransactionRepository transactionRepository;
    private final MemberRepository memberRepository;
    private final UserActivityLogService logService;
    private final BookRepository bookRepository;  // <-- add this

    public BorrowingTransactionService(BorrowingTransactionRepository transactionRepository,
                                       MemberRepository memberRepository,
                                       UserActivityLogService logService,BookRepository bookRepository) {
        this.transactionRepository = transactionRepository;
        this.memberRepository = memberRepository;
        this.logService = logService;
        this.bookRepository=bookRepository;
    }

    //  currently authenticated user
    private SystemUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof SystemUser user) {
            return user;
        }
        return null;
    }

    // Member borrows a book
    @Transactional
    public BorrowingTransaction borrowBook(BorrowingTransaction transaction, Long memberId) {
        SystemUser currentUser = getCurrentUser();

        // Find member
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId + " not found"));

        // Find book
        Book book = bookRepository.findById(transaction.getBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + transaction.getBook().getId() + " not found"));

        // Check availability
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is currently not available for borrowing.");
        }

        // Set book as unavailable
        book.setAvailable(false);
        bookRepository.save(book); // persist availability change

        // Set transaction fields
        transaction.setMember(member);
        transaction.setProcessedBy(currentUser);
        transaction.setBook(book);
        if (transaction.getBorrowDate() == null) {
            transaction.setBorrowDate(LocalDate.now());
        }

        // Save transaction
        BorrowingTransaction savedTransaction = transactionRepository.save(transaction);

        // Log activity
        logService.logActivity(currentUser, "BORROW_BOOK",
                "Member " + member.getName() + " borrowed book: " + book.getTitle());

        return savedTransaction;
    }



    // Member returns a book
    @Transactional
    public BorrowingTransaction returnBook(Long transactionId) {
        SystemUser currentUser = getCurrentUser();

        BorrowingTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with ID " + transactionId + " not found"));

        // If already returned, throw an exception (optional)
        if (transaction.isReturned()) {
            throw new IllegalStateException("This transaction has already been returned.");
        }

        // Mark transaction as returned
        transaction.setReturned(true);
        transaction.setReturnDate(LocalDate.now());
        transaction.setProcessedBy(currentUser);

        // Make the book available again
        transaction.getBook().setAvailable(true);
        bookRepository.save(transaction.getBook()); // save updated availability

        BorrowingTransaction saved = transactionRepository.save(transaction);

        logService.logActivity(currentUser, "RETURN_BOOK",
                "Member " + transaction.getMember().getName() + " returned book: " + transaction.getBook().getTitle());

        return saved;
    }
    @Transactional
    public BorrowingTransaction returnBookByBookId(Long bookId) {
        SystemUser currentUser = getCurrentUser();

        // Find the active borrowing transaction for this book
        BorrowingTransaction transaction = transactionRepository
                .findByBookIdAndReturnedFalse(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No active borrowing transaction found for book with ID " + bookId));

        Book book = transaction.getBook();
        if (book == null) {
            throw new ResourceNotFoundException("Book associated with this transaction not found");
        }

        // Check if the book is already marked available (extra safety)
        if (book.isAvailable()) {
            throw new IllegalStateException("Book is already available. Cannot return again.");
        }

        // Mark transaction as returned
        transaction.setReturned(true);
        transaction.setReturnDate(LocalDate.now());
        transaction.setProcessedBy(currentUser);

        // Mark book as available
        book.setAvailable(true);

        // Save both entities
        bookRepository.save(book);
        BorrowingTransaction savedTransaction = transactionRepository.save(transaction);

        // Log activity
        logService.logActivity(currentUser, "RETURN_BOOK",
                "Member " + transaction.getMember().getName() + " returned book: " + book.getTitle());

        return savedTransaction;
    }



    public List<BorrowingTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public BorrowingTransaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with ID " + id + " not found"));
    }

    public List<BorrowingTransaction> getTransactionsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId + " not found"));
        return transactionRepository.findByMember(member);
    }

    @Transactional
    public BorrowingTransaction updateTransaction(Long id, BorrowingTransaction updatedTransaction) {
        SystemUser currentUser = getCurrentUser();
        BorrowingTransaction transaction = getTransactionById(id);

        if (updatedTransaction.getBook() != null) transaction.setBook(updatedTransaction.getBook());
        if (updatedTransaction.getMember() != null) transaction.setMember(updatedTransaction.getMember());
        if (updatedTransaction.getBorrowDate() != null) transaction.setBorrowDate(updatedTransaction.getBorrowDate());
        if (updatedTransaction.getReturnDate() != null) transaction.setReturnDate(updatedTransaction.getReturnDate());
        transaction.setReturned(updatedTransaction.isReturned());
        transaction.setProcessedBy(currentUser);

        BorrowingTransaction saved = transactionRepository.save(transaction);

        logService.logActivity(currentUser, "UPDATE_BORROWING",
                "Updated borrowing transaction ID: " + saved.getId());

        return saved;
    }

    @Transactional
    public void deleteTransaction(Long id) {
        SystemUser currentUser = getCurrentUser();
        BorrowingTransaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);

        logService.logActivity(currentUser, "DELETE_BORROWING",
                "Deleted borrowing transaction ID: " + transaction.getId());
    }
}
