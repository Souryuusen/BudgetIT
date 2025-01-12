USE budget_it;

DELETE FROM users WHERE USER_ID != NULL;
DELETE FROM user_refresh_tokens WHERE refresh_token_id != NULL;

insert into users (user_id, user_active, user_creation_date, user_modification_date, user_pwd_hash, user_removal_date,
                   user_username)
values (1, 1, now(), now(), '$2a$10$CetFzebGhD61ihfVzAwSKOxAKCbB34AMuNzlXiTAFwwOLSllxrXHW', null, 'test');