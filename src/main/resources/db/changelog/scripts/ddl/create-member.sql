CREATE TABLE member (
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    manager_role VARCHAR(255),
    academic_title_code VARCHAR(255),
    education_title_code VARCHAR(255),
    scientific_field_code VARCHAR(255),
    department_name VARCHAR(255),
    PRIMARY KEY (first_name, last_name),
    CONSTRAINT fk_academic_title_codebook FOREIGN KEY (academic_title_code) REFERENCES academic_title_codebook (code),
    CONSTRAINT fk_education_title_codebook FOREIGN KEY (education_title_code) REFERENCES education_title_codebook (code),
    CONSTRAINT fk_scientific_field_codebook FOREIGN KEY (scientific_field_code) REFERENCES scientific_field_codebook (code),
    CONSTRAINT fk_member_from_department FOREIGN KEY (department_name) REFERENCES department (name)
)