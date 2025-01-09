import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubjectListComponent } from './features/subject/subject-list/subject-list.component';
import { DepartmentListComponent } from './features/department/department-list/department-list.component';
import { MemberListComponent } from './features/member/member-list/member-list.component';
import { AcademicTitleListComponent } from './features/academic-title/academic-title-list/academic-title-list.component';
import { EducationTitleListComponent } from './features/education-title/education-title-list/education-title-list.component';
import { ScientificFieldListComponent } from './features/scientific-field/scientific-field-list/scientific-field-list.component';

const routes: Routes = [
  { path: 'subjects', component: SubjectListComponent },
  { path: 'departments', component: DepartmentListComponent },
  { path: 'members', component: MemberListComponent },
  { path: 'academicTitles', component: AcademicTitleListComponent },
  { path: 'educationTitles', component: EducationTitleListComponent },
  { path: 'scientificFields', component: ScientificFieldListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
