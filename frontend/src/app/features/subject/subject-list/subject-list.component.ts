import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MetaData } from 'src/app/core/models/meta.data.interface';
import { Subject } from 'src/app/core/models/subject.model';
import { DepartmentService } from 'src/app/core/services/department/department.service';
import { SubjectService } from 'src/app/core/services/subject/subject.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

@Component({
  selector: 'app-subject-list',
  templateUrl: './subject-list.component.html',
  styleUrls: ['./subject-list.component.css'],
})
export class SubjectListComponent implements OnInit {
  metaData: MetaData[] = [
    { label: 'Subject Name', key: 'name', inputType: 'text' },
    { label: 'ESPB Points', key: 'espb', inputType: 'number', editable: true },
    {
      label: 'Department Name',
      key: 'department',
      inputType: 'dropdown',
      options: [], // Ovo će biti popunjeno dinamički},
    },
  ];

  model: Subject = new Subject(0, '', 0, '');
  subjects: Subject[] = [];
  isEditable: boolean = false;

  constructor(
    private subjectService: SubjectService,
    private departmentService: DepartmentService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    // Pretplaćujemo se na tok podataka
    this.subjectService.subjects$.subscribe((data) => {
      this.subjects = data;
    });

    // Osiguravamo da se podaci učitaju ako još nisu učitani
    this.subjectService.getSubjectsIfNeeded();

    this.loadDepartmentOptions();
  }

  private loadDepartmentOptions() {
    this.departmentService.getDepartments().subscribe((departments) => {
      // Mapiraj `departments` u format `{ label: string; value: string }`
      this.metaData[2].options = departments.map((dept) => ({
        label: dept.name, // Prikaz (ime)
        value: dept.name, // Vrednost (takođe ime)
      }));
    });
  }

  addNewSubject(): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      data: { columns: this.metaData, data: this.model, isEdit: false },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.subjectService.createSubject(result).subscribe(() => {
          alert('Subject created successfully!');
        });
      }
    });
  }

  // Brisanje predmeta
  deleteSubject(row: any): void {
    const id = row.id;
    if (confirm('Are you sure you want to delete this subject?')) {
      this.subjectService.deleteSubject(id).subscribe({
        next: () => {
          // Ukloni predmet iz lokalne liste
          this.subjects = this.subjects.filter((subject) => subject.id !== id);
          console.log('Subject deleted successfully.');
          alert('Subject successfully removed!');
        },
        error: (err) => {
          console.error('Error deleting subject:', err);
          alert(`Error deleting subject: ${err.message}`);
        },
      });
    }
  }

  // Ažuriranje ESPB poena
  updateESPB(updateData: { id: number; value: any }): void {
    this.subjectService
      .updateEspb(updateData.id, updateData.value)
      .subscribe(() => {
        alert('ESPB updated successfully!');
      });
  }
}
