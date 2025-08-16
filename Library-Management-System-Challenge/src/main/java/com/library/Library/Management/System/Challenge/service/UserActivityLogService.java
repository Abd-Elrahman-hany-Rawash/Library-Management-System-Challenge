package com.library.Library.Management.System.Challenge.service;

import com.library.Library.Management.System.Challenge.entity.SystemUser;
import com.library.Library.Management.System.Challenge.entity.UserActivityLog;
import com.library.Library.Management.System.Challenge.repo.UserActivityLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserActivityLogService {

    private final UserActivityLogRepository activityLogRepository;

    public UserActivityLogService(UserActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    // تسجيل النشاط مع تمرير المستخدم
    @Transactional
    public void logActivity(SystemUser user, String action, String details) {
        if (user == null) return;
        UserActivityLog log = new UserActivityLog();
        log.setUser(user);
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        activityLogRepository.save(log);
    }

    // تسجيل النشاط تلقائيًا من المستخدم الحالي في الـ SecurityContext
    @Transactional
    public void logActivity(String action, String details) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof SystemUser user) {
            logActivity(user, action, details);
        }
    }

    public List<UserActivityLog> getAllLogs() {
        return activityLogRepository.findAll();
    }
}
