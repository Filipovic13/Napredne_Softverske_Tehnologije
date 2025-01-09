import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MetaData } from 'src/app/core/models/meta.data.interface';

// interface Column {
//   header: string;
//   key: string;
//   editable?: boolean;
//   inputType: string;
//   options?: string[];
// }

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent {
  @Input() isEditable: boolean = true;
  @Input() isUpdatable: boolean = true;
  @Input() metaData: MetaData[] = []; // Definicija kolona
  @Input() data: any[] = []; // Podaci za prikaz
  @Input() model: any = {}; // Model klase sa praznim vrednostima za atribute
  @Output() edit = new EventEmitter<any>(); // Emitovanje izmena
  @Output() delete = new EventEmitter<any>(); // Emitovanje ID-a za brisanje
  @Output() add = new EventEmitter<void>(); // Emitovanje događaja za dodavanje
  @Output() update = new EventEmitter<{
    id: number;
    key: string;
    value: any;
  }>();

  // Emitovanje događaja za dodavanje
  onAddnew(): void {
    this.add.emit();
  }

  // Emitovanje događaja za ažuriranje polja
  onUpdateField(row: any): void {
    const editableColumns = this.metaData.filter(
      (col) => col.inputType !== 'dropdown'
    );
    editableColumns.forEach((col) => {
      const updatedValue = row[col.key];
      this.update.emit({ id: row.id, key: col.key, value: updatedValue });
    });
  }

  // Emitovanje događaja za editovanje
  onEdit(row: any): void {
    this.edit.emit(row);
  }

  // Emitovanje događaja za brisanje
  onDelete(row: any): void {
    this.delete.emit(row);
  }
}
