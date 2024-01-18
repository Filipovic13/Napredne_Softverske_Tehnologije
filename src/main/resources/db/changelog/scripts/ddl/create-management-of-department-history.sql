CREATE TABLE management_of_department_history (
    id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    start_date DATE,
    end_date DATE,
    manager_role VARCHAR(11),
    member_id BIGINT UNSIGNED,
    department_name VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_member_manager FOREIGN KEY (member_id) REFERENCES member (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_management_department FOREIGN KEY (department_name) REFERENCES department (name) ON UPDATE CASCADE ON DELETE CASCADE
);
