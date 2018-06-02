INSERT INTO users(username, password, enabled) VALUES ('admin', '$2a$10$yq4Rtai5TKLBQOu5QdE0DOeLGhToi434J0aFpM6YDj3DNoJ33fSji', true) on conflict do nothing;
INSERT INTO user_roles (user_role_id, username, role) VALUES (0, 'admin', 'ROLE_ADMIN') on conflict do nothing;
