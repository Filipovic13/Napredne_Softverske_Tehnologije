import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectListComponent } from './subject-list/subject-list.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [SubjectListComponent],
  imports: [CommonModule, SharedModule],
  exports: [SubjectListComponent],
})
export class SubjectModule {}
