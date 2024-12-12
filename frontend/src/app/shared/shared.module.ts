import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableComponent } from './table/table.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from '../app-routing.module';
import { ModalComponent } from './modal/modal.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';

@NgModule({
  declarations: [TableComponent, NavbarComponent, ModalComponent],
  imports: [
    CommonModule,
    AppRoutingModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule, // Ovo je potrebno za form-field omot
    MatOptionModule, // Ovo omogućava mat-option
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [TableComponent, NavbarComponent, ModalComponent], // Eksportuj da bi se koristilo u drugim modulima
})
export class SharedModule {}
