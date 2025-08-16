package com.library.Library.Management.System.Challenge.repo;

import com.library.Library.Management.System.Challenge.entity.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserActivityLogRepository extends JpaRepository<UserActivityLog,Long> {
}
