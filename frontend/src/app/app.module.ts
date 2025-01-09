import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from './shared/shared.module';
import { SubjectModule } from './features/subject/subject.module';
import { DepartmentModule } from './features/department/department.module';
import { MemberModule } from './features/member/member.module';
import { AcademicTitleModule } from './features/academic-title/academic-title.module';
import { EducationTitleModule } from './features/education-title/education-title.module';
import { ScientificFieldModule } from './features/scientific-field/scientific-field.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SharedModule,
    SubjectModule,
    DepartmentModule,
    MemberModule,
    AcademicTitleModule,
    EducationTitleModule,
    ScientificFieldModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
