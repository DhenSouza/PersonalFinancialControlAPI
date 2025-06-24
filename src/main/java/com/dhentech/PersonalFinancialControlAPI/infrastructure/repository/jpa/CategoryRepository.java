package com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa;

import com.dhentech.PersonalFinancialControlAPI.domain.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
