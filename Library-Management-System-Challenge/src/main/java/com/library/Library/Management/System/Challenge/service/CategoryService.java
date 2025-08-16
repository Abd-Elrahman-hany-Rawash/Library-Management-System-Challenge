package com.library.Library.Management.System.Challenge.service;

import com.library.Library.Management.System.Challenge.entity.Category;
import com.library.Library.Management.System.Challenge.entity.SystemUser;
import com.library.Library.Management.System.Challenge.repo.CategoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserActivityLogService logService;

    public CategoryService(CategoryRepository categoryRepository, UserActivityLogService logService) {
        this.categoryRepository = categoryRepository;
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
    public Category saveCategory(Category category) {
        Category saved = categoryRepository.save(category);
        logService.logActivity(getCurrentUser(), "CREATE_CATEGORY", "Created category: " + saved.getName());
        return saved;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Transactional
    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = getCategoryById(id);
        if (updatedCategory.getName() != null) category.setName(updatedCategory.getName());
        if (updatedCategory.getParentCategory() != null) category.setParentCategory(updatedCategory.getParentCategory());
        Category saved = categoryRepository.save(category);
        logService.logActivity(getCurrentUser(), "UPDATE_CATEGORY", "Updated category: " + saved.getName());
        return saved;
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
        logService.logActivity(getCurrentUser(), "DELETE_CATEGORY", "Deleted category: " + category.getName());
    }
}
