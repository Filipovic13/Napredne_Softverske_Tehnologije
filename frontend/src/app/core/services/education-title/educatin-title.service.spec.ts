import { TestBed } from '@angular/core/testing';

import { EducationTitleService } from './education-title.service';

describe('EducatinTitleService', () => {
  let service: EducationTitleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EducationTitleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
