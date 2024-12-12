import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent {
  form: FormGroup; // Forma koja se koristi za unos podataka
  isEdit: boolean = false;
  data: any;

  constructor(
    public dialogRef: MatDialogRef<ModalComponent>,
    @Inject(MAT_DIALOG_DATA) public passedData: any, // Prima podatke u formi objekta
    private fb: FormBuilder
  ) {
    this.isEdit = passedData.isEdit;
    this.form = this.fb.group({});

    // Ako je za editovanje, popunimo formu sa postojećim podacima
    if (this.isEdit) {
      this.data = passedData.passedData;
      for (let key in this.data) {
        this.form.addControl(key, this.fb.control(this.data[key])); // Dodavanje kontrola u formu
      }
    } else {
      this.data = passedData.passedData;
      // Ako je za dodavanje novog entiteta, kreiramo prazna polja za sve atribute
      for (let key in this.data) {
        this.form.addControl(key, this.fb.control('')); // Dodavanje praznih kontrola
      }
    }
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

  // Pomoćna metoda za dobijanje ključeva objekta (polja forme)
  objectKeys(obj: any): string[] {
    return Object.keys(obj); // Vraća sve ključeve objekta (atribute)
  }
}
