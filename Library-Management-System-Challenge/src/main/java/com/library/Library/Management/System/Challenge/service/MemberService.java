package com.library.Library.Management.System.Challenge.service;

import com.library.Library.Management.System.Challenge.ResourceNotFoundException;
import com.library.Library.Management.System.Challenge.entity.Member;
import com.library.Library.Management.System.Challenge.entity.SystemUser;
import com.library.Library.Management.System.Challenge.repo.BorrowingTransactionRepository;
import com.library.Library.Management.System.Challenge.repo.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserActivityLogService logService;
    private final BorrowingTransactionRepository transactionRepository;

    public MemberService(MemberRepository memberRepository, UserActivityLogService logService, BorrowingTransactionRepository transactionRepository) {
        this.memberRepository = memberRepository;
        this.logService = logService;
        this.transactionRepository=transactionRepository;
    }

    // Helper to get currently authenticated user
    private SystemUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof SystemUser user) {
            return user;
        }
        return null;
    }

    @Transactional
    public Member saveMember(Member member) {
        Member saved = memberRepository.save(member);
        logService.logActivity(getCurrentUser(), "CREATE_MEMBER", "Created member: " + saved.getName());
        return saved;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @Transactional
    public Member updateMember(Long id, Member updatedMember) {
        Member member = getMemberById(id);
        if (updatedMember.getName() != null) member.setName(updatedMember.getName());
        if (updatedMember.getEmail() != null) member.setEmail(updatedMember.getEmail());
        if (updatedMember.getPhone() != null) member.setPhone(updatedMember.getPhone());
        if (updatedMember.getMembershipDate() != null) member.setMembershipDate(updatedMember.getMembershipDate());

        Member saved = memberRepository.save(member);
        logService.logActivity(getCurrentUser(), "UPDATE_MEMBER", "Updated member: " + saved.getName());
        return saved;
    }

    @Transactional
    public void deleteMember(Long id) {
        // Find member or throw
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + id + " not found"));

        // Delete all related borrowing transactions first
        transactionRepository.deleteAllByMemberId(id);

        // Delete member
        memberRepository.delete(member);

        // Log activity
        SystemUser currentUser;
        try {
            currentUser = getCurrentUser();
        } catch (Exception e) {
            currentUser = new SystemUser();
            currentUser.setId(0L);
            currentUser.setUsername("SYSTEM");
        }

        logService.logActivity(currentUser, "DELETE_MEMBER", "Deleted member: " + member.getName());
    }



}
