import { Component, OnInit } from '@angular/core';
import { EducationTitle } from 'src/app/core/models/education-title.interface.ts';
import { MetaData } from 'src/app/core/models/meta.data.interface';
import { EducationTitleService } from 'src/app/core/services/education-title/education-title.service';
import { SharedModule } from '../../../shared/shared.module';

@Component({
  selector: 'app-education-title-list',
  templateUrl: './education-title-list.component.html',
  styleUrls: ['./education-title-list.component.css'],
})
export class EducationTitleListComponent implements OnInit {
  educationTitles: EducationTitle[] = [];
  metaData: MetaData[] = [
    { label: 'Code', key: 'code', inputType: 'text' },
    { label: 'Education Title', key: 'name', inputType: 'text' },
  ];

  constructor(private titleService: EducationTitleService) {}

  ngOnInit(): void {
    this.titleService.educationTitles$.subscribe(
      (data) => (this.educationTitles = data)
    );

    this.titleService.getEducationTitles().subscribe();
  }
}
