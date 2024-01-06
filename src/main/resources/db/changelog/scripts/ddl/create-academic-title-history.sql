CREATE TABLE academic_title_history(
    start_date DATE,
    end_date DATE,
    academic_title_code VARCHAR(255),
    scientific_field_code VARCHAR(255),
    member_first_name VARCHAR(255),
    member_last_name VARCHAR(255),
    PRIMARY KEY (start_date, member_first_name, member_last_name),
    CONSTRAINT fk_academic_title_history_of_member FOREIGN KEY (member_first_name, member_last_name) REFERENCES member (first_name, last_name),
    CONSTRAINT fk_academic_title FOREIGN KEY (academic_title_code) REFERENCES academic_title_codebook (code),
    CONSTRAINT fk_scientific_field FOREIGN KEY (scientific_field_code) REFERENCES scientific_field_codebook (code)
)