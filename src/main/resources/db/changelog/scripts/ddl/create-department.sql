CREATE TABLE department (
    name VARCHAR(255) PRIMARY KEY NOT NULL,
    short_name VARCHAR(255) NOT NULL,
    supervisor_id BIGINT UNSIGNED,
    secretary_id BIGINT UNSIGNED
);