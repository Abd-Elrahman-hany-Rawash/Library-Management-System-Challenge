package com.library.Library.Management.System.Challenge.repo;

import com.library.Library.Management.System.Challenge.entity.BorrowingTransaction;
import com.library.Library.Management.System.Challenge.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByMemberId(Long memberId);
    List<BorrowingTransaction> findByMember(Member member);
    Optional<BorrowingTransaction> findByBookIdAndReturnedFalse(Long bookId);
    @Modifying
    @Transactional
    @Query("DELETE FROM BorrowingTransaction t WHERE t.member.id = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);

}