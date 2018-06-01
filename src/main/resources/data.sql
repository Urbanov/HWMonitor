INSERT INTO users(username, password, enabled) VALUES ('jack','$2a$10$bd4Qn3nFAGmg3hdohmyZfuj9.ibNEkfFQlqdDlPMwA0ZCkbg7Arr.', true) on conflict do nothing;
INSERT INTO users(username, password, enabled) VALUES ('peter','$2a$10$H6I9wy8Tvv9DbA14IiEQ9eBLU/QFDhqYGG9FUXlGGdRCSgtOCjAqS', true) on conflict do nothing;

INSERT INTO user_roles (username, role) VALUES ('jack', 'ROLE_USER') on conflict do nothing;
INSERT INTO user_roles (username, role) VALUES ('jack', 'ROLE_DRUGA') on conflict do nothing;
INSERT INTO user_roles (username, role) VALUES ('jack', 'ROLE_PIERWSZA') on conflict do nothing;
INSERT INTO user_roles (username, role) VALUES ('peter', 'ROLE_USER') on conflict do nothing;
INSERT INTO user_roles (username, role) VALUES ('peter', 'ROLE_PIERWSZA') on conflict do nothing;

INSERT INTO companies (company_id, company_name, company_role) VALUES (1, 'PIERWSZA', 'ROLE_PIERWSZA') on conflict do nothing;
INSERT INTO companies (company_id, company_name, company_role) VALUES (2, 'DRUGA', 'ROLE_DRUGA') on conflict do nothing;

INSERT INTO feeders (serial, company_id, feeder_desc, secret) VALUES (1234567, 2, 'pierwszy feeder firmy DRUGA','s1') on conflict do nothing;
INSERT INTO feeders (serial, company_id, feeder_desc, secret) VALUES (2345678, 1, 'pierwszy feeder firmy PIERWSZA','s2') on conflict do nothing;
INSERT INTO feeders (serial, company_id, feeder_desc, secret) VALUES (3456789, 2, 'drugi feeder firmy DRUGA','s3') on conflict do nothing;
INSERT INTO feeders (serial, company_id, feeder_desc, secret) VALUES (4567890, 1, 'drugi feeder firmy PIERWSZA','s4') on conflict do nothing;
