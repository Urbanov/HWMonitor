CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS users (
   username VARCHAR(20) NOT NULL PRIMARY KEY,
   password VARCHAR(60) NOT NULL,
   enabled BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS companies (
   company_id SERIAL PRIMARY KEY,
   company_name VARCHAR(10) NOT NULL UNIQUE,
   company_role VARCHAR(20) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS FEEDERS_SEQ
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS feeders (
   feeder_id INTEGER PRIMARY KEY DEFAULT NEXTVAL('FEEDERS_SEQ'),
   serial INTEGER NOT NULL,
   company_id INTEGER NOT NULL,
   feeder_desc VARCHAR(64) NOT NULL,
   secret VARCHAR(16) NOT NULL,
   UNIQUE (serial, company_id),
   FOREIGN KEY (company_id) REFERENCES companies (company_id)
);

CREATE TABLE IF NOT EXISTS measurements (
   id SERIAL PRIMARY KEY,
   feeder_id INTEGER NOT NULL,
   value INTEGER NOT NULL,
   time TIMESTAMP NOT NULL,
   FOREIGN KEY (feeder_id) REFERENCES feeders (feeder_id)
);

CREATE TABLE IF NOT EXISTS user_roles (
  user_role_id SERIAL PRIMARY KEY,
  username VARCHAR(20) NOT NULL,
  role VARCHAR(20) NOT NULL,
  UNIQUE (username, role),
  FOREIGN KEY (username) REFERENCES users (username)
);