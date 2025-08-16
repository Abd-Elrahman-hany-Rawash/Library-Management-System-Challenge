package com.library.Library.Management.System.Challenge.repo;

import com.library.Library.Management.System.Challenge.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MemberRepository extends JpaRepository<Member, Long> {
}
