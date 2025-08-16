package com.library.Library.Management.System.Challenge.controller;

import com.library.Library.Management.System.Challenge.ResourceNotFoundException;
import com.library.Library.Management.System.Challenge.entity.BorrowingTransaction;
import com.library.Library.Management.System.Challenge.service.BorrowingTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member/transactions")
public class BorrowingTransactionController {

    private final BorrowingTransactionService transactionService;

    public BorrowingTransactionController(BorrowingTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //  Member borrows a book
    @PostMapping("/borrow")
    // @PreAuthorize("hasAnyRole('ADMIN','STAFF')") // Enable security later
    public ResponseEntity<BorrowingTransaction> borrowBook(
            @RequestParam Long memberId,
            @RequestBody BorrowingTransaction transaction
    ) {
        try {
            return ResponseEntity.ok(transactionService.borrowBook(transaction, memberId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //  Return a book by transaction ID
    @PutMapping("/{transactionId}/return")
    // @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<?> returnBook(@PathVariable Long transactionId) {
        try {
            BorrowingTransaction transaction = transactionService.returnBook(transactionId);
            return ResponseEntity.ok(transaction);
        } catch (ResourceNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Return a book by book ID
    @PutMapping("/return/book/{bookId}")
    // @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<?> returnBookByBookId(@PathVariable Long bookId) {
        try {
            BorrowingTransaction transaction = transactionService.returnBookByBookId(bookId);
            return ResponseEntity.ok(transaction);
        } catch (ResourceNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    //  Get all transactions of a member
    @GetMapping("/member/{memberId}")
    // @PreAuthorize("hasAnyRole('ADMIN','STAFF','MEMBER')") /
    public ResponseEntity<List<BorrowingTransaction>> getTransactionsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(transactionService.getTransactionsByMember(memberId));
    }

    // Get a single transaction
    @GetMapping("/{transactionId}")
    // @PreAuthorize("hasAnyRole('ADMIN','STAFF','MEMBER')") // Enable security later
    public ResponseEntity<?> getTransactionById(@PathVariable Long transactionId) {
        try {
            BorrowingTransaction transaction = transactionService.getTransactionById(transactionId);
            return ResponseEntity.ok(transaction);
        } catch (ResourceNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(404).body(error);
        }
    }

    //  Update a transaction (extend return date etc.)\
    @PutMapping("/{transactionId}")
    // @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<?> updateTransaction(
            @PathVariable Long transactionId,
            @RequestBody BorrowingTransaction updatedTransaction
    ) {
        try {
            BorrowingTransaction transaction = transactionService.updateTransaction(transactionId, updatedTransaction);
            return ResponseEntity.ok(transaction);
        } catch (ResourceNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Delete a transaction
    @DeleteMapping("/{transactionId}")
    // @PreAuthorize("hasRole('ADMIN')") // Enable security later
    public ResponseEntity<?> deleteTransaction(@PathVariable Long transactionId) {
        try {
            transactionService.deleteTransaction(transactionId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
