package com.library.Library.Management.System.Challenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "borrowing_transactions")
public class BorrowingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties({"authors", "publisher", "category"}) // prevent recursion
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonIgnoreProperties("borrowings") // prevent recursion in Member
    private Member member;

    @ManyToOne
    @JoinColumn(name = "processed_by_user_id")
    @JsonIgnoreProperties({"activityLogs", "processedTransactions"})
    private SystemUser processedBy; // staff/librarian who processes the transaction

    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;
}
