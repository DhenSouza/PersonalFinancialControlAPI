package com.dhentech.PersonalFinancialControlAPI.domain.model.category;

import com.dhentech.PersonalFinancialControlAPI.domain.model.Auditing.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category extends Auditable {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    public Category() {}

    public Category(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be blank.");
        }

        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void updateName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be blank.");
        }
        this.setName(newName);
    }

}
