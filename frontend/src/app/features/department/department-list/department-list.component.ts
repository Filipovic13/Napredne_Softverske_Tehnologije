import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MetaData } from 'src/app/core/models/meta.data.interface';
import { Department } from 'src/app/core/models/department.display.interface';
import { Member } from 'src/app/core/models/member.interface';
import { DepartmentService } from 'src/app/core/services/department/department.service';
import { MemberService } from 'src/app/core/services/member/member.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { ManagementOfDepartmentHistorService } from 'src/app/core/services/management-of-department-history/management-of-department-histor.service';

@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css'],
})
export class DepartmentListComponent implements OnInit {
  metaData: MetaData[] = [
    {
      label: 'Department name',
      key: 'name',
      editable: true,
      inputType: 'text',
    },
    { label: 'Short name', key: 'shortName', inputType: 'text' },
    {
      label: 'Supervisor',
      key: 'supervisorId',
      inputType: 'dropdown',
      options: [],
      formatter: (row) => this.getMemberFullName(row.supervisorId),
    },
    {
      label: 'Secretary',
      key: 'secretaryId',
      inputType: 'dropdown',
      options: [],
      formatter: (row) => this.getMemberFullName(row.secretaryId),
    },
  ];

  historyMetaData: MetaData[] = [
    { label: 'Start Date', key: 'startDate', inputType: 'text' },
    { label: 'End Date', key: 'endDate', inputType: 'text' },
    { label: 'Role', key: 'managerRole', inputType: 'text' },
    {
      label: 'Member',
      key: 'memberId',
      inputType: 'text',
      formatter: (row) => this.getMemberFullName(row.memberId),
    },
  ];

  departments: Department[] = [];
  members: Member[] = [];
  isEditable: boolean = false;
  model = {};
  // model = {
  //   id: 0,
  //   firstName: '',
  //   lastName: '',
  //   academicTitle: '',
  //   educationTitle: '',
  //   scientificField: '',
  //   departmentName: '',
  // };

  constructor(
    private departmentService: DepartmentService,
    private managementOfDepartmentHistorService: ManagementOfDepartmentHistorService,
    private memberService: MemberService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.departmentService.departments$.subscribe((data: Department[]) => {
      this.departments = data;
    });
    this.memberService.members$.subscribe((data: Member[]) => {
      this.members = data;
      console.log(this.members);
      this.loadMemberOptions();
    });
    this.fetchDepartmentsAndMembers();
  }

  private fetchDepartmentsAndMembers(): void {
    this.departmentService
      .getDepartments()
      .subscribe((departments: Department[]) => {
        this.memberService.getMembers().subscribe((members: Member[]) => {
          this.members = members;

          this.departments = departments.map((d) => ({
            ...d,
            supervisorFullName: this.getMemberFullName(d.supervisorId),
            secretaryFullName: this.getMemberFullName(d.secretaryId),
          }));
        });
      });
  }

  private getMemberFullName(memberId: number): string {
    const member = this.members.find((m) => m.id == memberId);
    return member ? `${member.firstName} ${member.lastName}` : 'Unknowrn';
  }

  private loadMemberOptions() {
    // Popunjavanje dropdown opcija sa imenom i prezimenom, a vrednosti id
    const memberOptions = this.members.map((member) => ({
      label: `${member.firstName} ${member.lastName}`,
      value: member.id,
    }));

    // Dodavanje članova u dropdown opcije za supervizora i sekretara
    this.metaData[2].options = memberOptions; // Supervizori
    this.metaData[3].options = memberOptions; // Sekretari
  }

  addNewDepartment(): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      data: { columns: this.metaData, data: this.model, isEdit: false },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.departmentService.createDepartment(result).subscribe(() => {
          console.log(result);
          alert('Department crated succesfully!');
        });
      }
    });
  }

  deleteDepartment(row: any): void {
    const name = row.name;
    if (confirm('Are you sure you want to delete this department?')) {
      this.departmentService.deleteDepartment(name).subscribe({
        next: () => {
          console.log('Dapartment deleted successfully.');
        },
        error: (err) => {
          console.error('Error deleting subject:', err);
        },
      });
    }
  }
}
