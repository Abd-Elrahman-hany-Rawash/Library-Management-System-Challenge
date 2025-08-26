package com.library.Library.Management.System.Challenge.controller;

import com.library.Library.Management.System.Challenge.ResourceNotFoundException;
import com.library.Library.Management.System.Challenge.entity.Member;
import com.library.Library.Management.System.Challenge.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //  Create a new member
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','STAFF','LIBRARIAN')") // Enable security later
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        try {
            Member savedMember = memberService.saveMember(member);
            return ResponseEntity.ok(savedMember);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get a single member by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','STAFF','LIBRARIAN')") // Enable security later
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        try {
            Member member = memberService.getMemberById(id);
            return ResponseEntity.ok(member);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    //  Get all members
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','STAFF','LIBRARIAN')") // Enable security later

    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    // Update a member
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','STAFF','LIBRARIAN')") // Enable security later
    public ResponseEntity<Member> updateMember(
            @PathVariable Long id,
            @RequestBody Member updatedMember
    ) {
        try {
            Member member = memberService.updateMember(id, updatedMember);
            return ResponseEntity.ok(member);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    //  Delete a member
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR',)") // Enable security later
    public ResponseEntity<Map<String, String>> deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Member deleted successfully");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(404).body(error);
        }
    }
}
