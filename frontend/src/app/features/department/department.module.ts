import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepartmentListComponent } from './department-list/department-list.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [DepartmentListComponent],
  imports: [CommonModule, SharedModule],
  exports: [DepartmentListComponent],
})
export class DepartmentModule {}
