-- Creates the 'categories' table
CREATE TABLE categories (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Adds a unique constraint to the 'name' column for data integrity
ALTER TABLE categories
    ADD CONSTRAINT UK_categories_name UNIQUE (name);