import { Component, OnInit } from '@angular/core';
import { Department } from 'src/app/core/models/department.interface';
import { DepartmentService } from 'src/app/core/services/department/department.service';

@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css'],
})
export class DepartmentListComponent implements OnInit {
  columns = [
    { header: 'Department name', key: 'name', editable: true },
    { header: 'Short name', key: 'shortName' },
    { header: 'Supervisor', key: 'supervisorFullName' },
    { header: 'Secretary', key: 'secretaryFullName' },
  ];

  departments: Department[] = [];
  isEditable: boolean = false;

  constructor(private departmentService: DepartmentService) {}

  ngOnInit(): void {
    this.departmentService.departments$.subscribe((data) => {
      this.departments = data;
      console.log(this.departments);
    });

    this.departmentService.getDepartmentsIfNeeded();
  }
}
