ALTER TABLE categories
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE,
ADD COLUMN updated_at TIMESTAMP WITHOUT TIME ZONE;

-- Optional: Update existing rows to have a non-null value for the new columns.
-- This sets the creation and update time to the current time for all existing records.
UPDATE categories SET created_at = NOW(), updated_at = NOW() WHERE created_at IS NULL;

-- Now, make the columns non-nullable for all future records.
ALTER TABLE categories
    ALTER COLUMN created_at SET NOT NULL,
ALTER COLUMN updated_at SET NOT NULL;