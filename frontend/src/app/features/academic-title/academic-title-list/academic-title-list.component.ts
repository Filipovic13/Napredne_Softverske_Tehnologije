import { Component, OnInit } from '@angular/core';
import { AcademicTitle } from 'src/app/core/models/academic-title.interface';
import { MetaData } from 'src/app/core/models/meta.data.interface';
import { AcademicTitleService } from 'src/app/core/services/academic-title/academic-title.service';

@Component({
  selector: 'app-academic-title-list',
  templateUrl: './academic-title-list.component.html',
  styleUrls: ['./academic-title-list.component.css'],
})
export class AcademicTitleListComponent implements OnInit {
  academicTitles: AcademicTitle[] = [];
  metaData: MetaData[] = [
    { label: 'Code', key: 'code', inputType: 'text' },
    { label: 'Academic Title', key: 'name', inputType: 'text' },
  ];

  constructor(private titleService: AcademicTitleService) {}

  ngOnInit(): void {
    this.titleService.academicTitles$.subscribe((data) => {
      this.academicTitles = data;
    });

    this.titleService.getAcademicTitles().subscribe();
  }
}
