import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subject } from 'src/app/core/models/subject.model';
import { SubjectService } from 'src/app/core/services/subject/subject.service';

@Component({
  selector: 'app-subject-list',
  templateUrl: './subject-list.component.html',
  styleUrls: ['./subject-list.component.css'],
})
export class SubjectListComponent implements OnInit {
  columns = [
    { header: 'Subject Name', key: 'name' },
    { header: 'ESPB Points', key: 'espb', editable: true },
    { header: 'Department Name', key: 'department' },
  ];

  model: Subject = new Subject(0, '', 0, '');
  subjects: Subject[] = [];
  isEditable: boolean = true;

  constructor(private subjectService: SubjectService) {}

  ngOnInit(): void {
    // Pretplaćujemo se na tok podataka
    this.subjectService.subjects$.subscribe((data) => {
      this.subjects = data;
      console.log('Subjects loaded:', this.subjects);
    });

    // Osiguravamo da se podaci učitaju ako još nisu učitani
    this.subjectService.getSubjectsIfNeeded();
  }

  // Dodavanje novog predmeta
  addNewSubject(newSubject: Subject): void {
    this.subjectService.createSubject(newSubject).subscribe({
      next: () => {
        console.log('Subject created successfully.');
        alert('Subject created successfully!');
      },
      error: (err) => {
        console.error('Error creating subject:', err);
        alert(`Error creating subject: ${err.message}`);
      },
    });
  }

  // Brisanje predmeta
  deleteSubject(id: number): void {
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

  // Ažuriranje polja (npr. ESPB)
  updateESPB(updatedData: { id: number; key: string; value: any }): void {
    const { id, key, value } = updatedData;

    if (key === 'espb') {
      this.subjectService.updateEspb(id, value).subscribe({
        next: (response) => {
          console.log(`Field "${key}" updated successfully:`, response);
          alert(`Field ${key} updated successfully to ${value}`);
          this.subjects = this.subjects.map((subject) =>
            subject.id === id ? { ...subject, [key]: value } : subject
          );
        },
        error: (err) => {
          console.error(`Error updating field "${key}":`, err);
          alert(`Error updating field "${key}": ${err.message}`);
        },
      });
    } else {
      console.warn('Updating other fields is not implemented yet.');
    }
  }
}
