import { Component, OnInit } from '@angular/core';
import { MetaData } from 'src/app/core/models/meta.data.interface';
import { ScientificField } from 'src/app/core/models/scientific-field.interface';
import { ScientificFieldService } from 'src/app/core/services/scientific-field/scientific-field.service';

@Component({
  selector: 'app-scientific-field-list',
  templateUrl: './scientific-field-list.component.html',
  styleUrls: ['./scientific-field-list.component.css'],
})
export class ScientificFieldListComponent implements OnInit {
  scientificFields: ScientificField[] = [];
  metaData: MetaData[] = [
    { label: 'Code', key: 'code', inputType: 'text' },
    { label: 'Scientific Field', key: 'name', inputType: 'text' },
  ];

  constructor(private scientificFieldsService: ScientificFieldService) {}

  ngOnInit(): void {
    this.scientificFieldsService.scientificFields$.subscribe(
      (data) => (this.scientificFields = data)
    );

    this.scientificFieldsService.getScientificFields().subscribe();
  }
}
