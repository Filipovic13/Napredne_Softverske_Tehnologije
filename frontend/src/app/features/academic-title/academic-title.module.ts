import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AcademicTitleListComponent } from './academic-title-list/academic-title-list.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [AcademicTitleListComponent],
  imports: [CommonModule, SharedModule],
  exports: [AcademicTitleListComponent],
})
export class AcademicTitleModule {}
