# Drop all stored data
    USE budget_it;
    DROP TABLE IF EXISTS budget_it;
# Initialize Test User
    USE budget_it;
    INSERT INTO users(
                      user_id, user_active, user_creation_date, user_modification_date,
                      user_pwd_hash, user_removal_date, user_username)
        VALUES (1, 1, NOW(), NOW(), '$2a$10$CetFzebGhD61ihfVzAwSKOxAKCbB34AMuNzlXiTAFwwOLSllxrXHW', null, 'test');
