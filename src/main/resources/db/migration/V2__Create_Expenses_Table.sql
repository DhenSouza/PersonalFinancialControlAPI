-- Creates the 'expenses' table
CREATE TABLE expenses (
    id UUID NOT NULL,
    amount NUMERIC(38, 2) NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(255) NOT NULL,
    category_id UUID NOT NULL,
    PRIMARY KEY (id)
);

-- Adds a foreign key constraint to link 'expenses' to 'categories'
ALTER TABLE expenses
    ADD CONSTRAINT FK_expenses_on_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id);