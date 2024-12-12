CREATE TABLE academic_title_history(
    id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    start_date DATE,
    end_date DATE,
    academic_title_code VARCHAR(255),
    scientific_field_code VARCHAR(255),
    member_id BIGINT UNSIGNED,
    PRIMARY KEY (id),
    CONSTRAINT fk_academic_title_history_of_member FOREIGN KEY (member_id) REFERENCES member (id)  ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_academic_title FOREIGN KEY (academic_title_code) REFERENCES academic_title_codebook (code) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_scientific_field FOREIGN KEY (scientific_field_code) REFERENCES scientific_field_codebook (code) ON UPDATE CASCADE ON DELETE SET NULL
);