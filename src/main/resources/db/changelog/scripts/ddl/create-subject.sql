CREATE TABLE subject(
    id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    espb INT NOT NULL,
    department_name VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_subject_in_department FOREIGN KEY (department_name) REFERENCES department (name)
)