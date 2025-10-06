-- Insert Authorities
INSERT INTO authorities (name, description) VALUES 
('USER_MANAGEMENT', 'Manage users'),
('ROLE_MANAGEMENT', 'Manage roles'),
('PERMISSION_MANAGEMENT', 'Manage permissions'),
('GROUP_MANAGEMENT', 'Manage groups');

-- Insert Permissions
INSERT INTO permissions (name, description, authority_fk) VALUES 
('CREATE_USER', 'Create new users', 1),
('READ_USER', 'View users', 1),
('UPDATE_USER', 'Edit users', 1),
('DELETE_USER', 'Delete users', 1),
('CREATE_ROLE', 'Create new roles', 2),
('READ_ROLE', 'View roles', 2),
('UPDATE_ROLE', 'Edit roles', 2),
('DELETE_ROLE', 'Delete roles', 2),
('CREATE_PERMISSION', 'Create new permissions', 3),
('READ_PERMISSION', 'View permissions', 3),
('UPDATE_PERMISSION', 'Edit permissions', 3),
('DELETE_PERMISSION', 'Delete permissions', 3),
('CREATE_GROUP', 'Create new groups', 4),
('READ_GROUP', 'View groups', 4),
('UPDATE_GROUP', 'Edit groups', 4),
('DELETE_GROUP', 'Delete groups', 4);

-- Insert Roles
INSERT INTO roles (name, description) VALUES 
('ADMIN', 'Administrator with full access'),
('USER', 'Regular user with limited access'),
('MANAGER', 'Manager with elevated privileges');

-- Assign Permissions to Roles
-- ADMIN gets all permissions
INSERT INTO role_permissions (role_fk, permission_fk) 
SELECT 1, id FROM permissions;

-- USER gets basic read permissions
INSERT INTO role_permissions (role_fk, permission_fk) VALUES 
(2, 2), (2, 6), (2, 10), (2, 14);

-- MANAGER gets additional permissions
INSERT INTO role_permissions (role_fk, permission_fk) VALUES 
(3, 1), (3, 2), (3, 3), (3, 5), (3, 6), (3, 7), (3, 9), (3, 10), (3, 11), (3, 13), (3, 14), (3, 15);

-- Insert Groups
INSERT INTO groups (name, description) VALUES 
('IT_DEPARTMENT', 'Information Technology Department'),
('HR_DEPARTMENT', 'Human Resources Department'),
('FINANCE_DEPARTMENT', 'Finance Department');

-- Assign Roles to Groups
INSERT INTO group_roles (group_fk, role_fk) VALUES 
(1, 1), -- IT gets ADMIN
(2, 3), -- HR gets MANAGER
(3, 2); -- Finance gets USER

-- Insert Admin User (password is "admin123")
INSERT INTO users (username, email, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES 
('admin', 'admin@example.com', '$2a$12$QTimz.Aqo014RCzXq5T.c.wpwOn3Ysk.Q34N7n9RfXlMgBTeJ642m', true, true, true, true),
('user', 'usertest@example.com', '$2a$12$QTimz.Aqo014RCzXq5T.c.wpwOn3Ysk.Q34N7n9RfXlMgBTeJ642m', true, true, true, true);

-- Assign Admin Role to Admin User
INSERT INTO user_roles (user_fk, role_fk) VALUES (1, 1);

-- Assign Admin to IT Group
INSERT INTO user_groups (user_fk, group_fk) VALUES (1, 1);