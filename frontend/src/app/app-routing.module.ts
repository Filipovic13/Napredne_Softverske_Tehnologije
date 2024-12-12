import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubjectListComponent } from './features/subject/subject-list/subject-list.component';
import { DepartmentListComponent } from './features/department/department-list/department-list.component';

const routes: Routes = [
  { path: 'subjects', component: SubjectListComponent },
  { path: 'departments', component: DepartmentListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
