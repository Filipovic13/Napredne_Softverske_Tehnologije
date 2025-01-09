import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EducationTitleListComponent } from './education-title-list/education-title-list.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [EducationTitleListComponent],
  imports: [CommonModule, SharedModule],
  exports: [EducationTitleListComponent],
})
export class EducationTitleModule {}
