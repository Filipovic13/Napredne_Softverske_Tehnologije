// table.component.ts
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

interface Column {
  header: string;
  key: string;
  editable?: boolean;
}

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent {
  @Input() isEditable: boolean = true;
  @Input() isUpdatable: boolean = true;
  @Input() columns: Column[] = []; // Definicija kolona
  @Input() data: any[] = []; // Podaci za prikaz
  @Input() model: any = {}; // Model klase sa praznim vrednostima za atribute
  @Output() edit = new EventEmitter<any>(); // Emitovanje izmena
  @Output() delete = new EventEmitter<number>(); // Emitovanje ID-a za brisanje
  @Output() add = new EventEmitter<any>(); // Emitovanje novih podataka
  @Output() update = new EventEmitter<{
    id: number;
    key: string;
    value: any;
  }>();

  constructor(public dialog: MatDialog) {}

  // Funkcija za dodavanje novih podataka (otvara modal)
  onAddnew(): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      data: {
        passedData: this.model, // Prosleđujemo model sa praznim vrednostima
        isEdit: false, // Ovo je za unos, a ne izmenu
      },
    });

    dialogRef.afterClosed().subscribe((newData) => {
      if (newData) {
        this.add.emit(newData); // Emitujemo nove podatke
      }
    });
  }

  onUpdateField(row: any): void {
    const editableColumns = this.columns.filter((col) => col.editable); // Nađi sve editable kolone
    editableColumns.forEach((col) => {
      const updatedValue = row[col.key]; // Uzmite vrednost iz reda
      // Emitovanje događaja ili pozivanje funkcije za ažuriranje
      this.updateField(row.id, col.key, updatedValue);
    });
  }

  // Emitovanje događaja ka roditeljskoj komponenti za ažuriranje
  updateField(rowId: number, fieldKey: string, updatedValue: any): void {
    this.update.emit({ id: rowId, key: fieldKey, value: updatedValue });
  }

  // Funkcija za editovanje postojećeg entiteta (otvara modal)
  onEdit(row: any): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      data: {
        passedData: row, // Prosleđujemo podatke za editovanje
        isEdit: true, // Modal je za editovanje
      },
    });

    dialogRef.afterClosed().subscribe((updatedData) => {
      if (updatedData) {
        this.edit.emit(updatedData); // Emitujemo ažurirane podatke
      }
    });
  }

  // Funkcija za brisanje entiteta
  onDelete(id: number): void {
    this.delete.emit(id); // Emituje ID entiteta za brisanje
  }
}
