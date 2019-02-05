INSERT IGNORE INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('medium', '$2a$10$jGZcjBOv6l7tnhJIoADDEusEAOssobfvI4sfF4GsgdAH3qQtGmBmC', 'read,write',
	'password,refresh_token', null, null, 36000, 36000, null, true);

INSERT IGNORE INTO oauth_users
	(email, enabled, mobile_number, name, password, surname, username)
VALUES
	('admin@admin.com', b'1', '3333333333', 'admin', '$2y$10$PiwMUKtnOysz4yOiuiGwrucwkFVHz0UtNv9e1OYn7b.C7sAgDAIFm', 'admin', 'admin');

INSERT IGNORE INTO oauth_users
	(email, enabled, mobile_number, name, password, surname, username)
VALUES
	('test@test.com', b'1', '3333333334', 'test', '$2y$10$PiwMUKtnOysz4yOiuiGwrucwkFVHz0UtNv9e1OYn7b.C7sAgDAIFm', 'test', 'test');

INSERT IGNORE INTO oauth_users_role
	(role, username)
VALUES
	('ADMIN', 'admin');

INSERT IGNORE INTO oauth_users_role
	(role, username)
VALUES
	('USER', 'test');