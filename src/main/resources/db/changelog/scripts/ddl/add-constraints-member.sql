ALTER TABLE member
    ADD CONSTRAINT fk_belongs_to_department FOREIGN KEY (department_name) REFERENCES department (name) ON UPDATE CASCADE ON DELETE SET NULL;