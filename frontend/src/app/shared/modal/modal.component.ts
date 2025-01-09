import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent {
  form: FormGroup; // Forma koja se koristi za unos podataka
  isEdit: boolean = false; // Da li je modal za editovanje ili dodavanje
  metaData: any[]; // Kolone za dinamičko generisanje forme
  data: any; // Podaci koji dolaze iz roditeljskog komponente

  constructor(
    public dialogRef: MatDialogRef<ModalComponent>,
    @Inject(MAT_DIALOG_DATA) public passedData: any, // Prima podatke u formi objekta
    private fb: FormBuilder
  ) {
    this.isEdit = passedData.isEdit;
    this.metaData = passedData.columns;
    this.data = passedData.data || {}; // Ako nemamo podatke, postavljamo praznu vrednost
    this.form = this.fb.group({});
    this.buildForm();
  }

  // Metoda za generisanje forme na osnovu kolona
  buildForm(): void {
    this.metaData.forEach((field) => {
      const validators = [];
      if (field.required) {
        validators.push(Validators.required);
      }

      // Dinamički kreiramo kontrolu u formi
      const control = this.fb.control(this.data[field.key] || '', validators);
      this.form.addControl(field.key, control);
    });
  }

  // Zatvori modal bez promene
  onClose(): void {
    this.dialogRef.close();
  }

  // Pošaljemo izmenjene podatke
  onSave(): void {
    if (this.form.valid) {
      this.dialogRef.close(this.form.value); // Zatvori modal i pošaljemo podatke roditelju
    }
  }
}
