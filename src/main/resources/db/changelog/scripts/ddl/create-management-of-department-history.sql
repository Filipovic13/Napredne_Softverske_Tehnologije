CREATE TABLE management_of_department_history (
    start_date DATE,
    end_date DATE,
    supervisor_first_name VARCHAR(255),
    supervisor_last_name VARCHAR(255),
    secretary_first_name VARCHAR(255),
    secretary_last_name VARCHAR(255),
    department_name VARCHAR(255),
    PRIMARY KEY (start_date, department_name),
    CONSTRAINT fk_management_department FOREIGN KEY (department_name) REFERENCES department (name)
);
