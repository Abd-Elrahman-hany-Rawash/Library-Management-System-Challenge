package com.library.Library.Management.System.Challenge.service;

import com.library.Library.Management.System.Challenge.entity.Publisher;
import com.library.Library.Management.System.Challenge.entity.SystemUser;
import com.library.Library.Management.System.Challenge.repo.PublisherRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final UserActivityLogService logService;

    public PublisherService(PublisherRepository publisherRepository, UserActivityLogService logService) {
        this.publisherRepository = publisherRepository;
        this.logService = logService;
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
    public Publisher savePublisher(Publisher publisher) {
        Publisher saved = publisherRepository.save(publisher);
        logService.logActivity(getCurrentUser(), "CREATE_PUBLISHER", "Created publisher: " + saved.getName());
        return saved;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
    }

    @Transactional
    public Publisher updatePublisher(Long id, Publisher updatedPublisher) {
        Publisher publisher = getPublisherById(id);
        if (updatedPublisher.getName() != null) publisher.setName(updatedPublisher.getName());
        Publisher saved = publisherRepository.save(publisher);
        logService.logActivity(getCurrentUser(), "UPDATE_PUBLISHER", "Updated publisher: " + saved.getName());
        return saved;
    }

    @Transactional
    public void deletePublisher(Long id) {
        Publisher publisher = getPublisherById(id);
        publisherRepository.delete(publisher);
        logService.logActivity(getCurrentUser(), "DELETE_PUBLISHER", "Deleted publisher: " + publisher.getName());
    }
}
