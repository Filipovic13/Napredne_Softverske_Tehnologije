import { TestBed } from '@angular/core/testing';

import { AcademicTitleService } from './academic-title.service';

describe('AcademicTitleService', () => {
  let service: AcademicTitleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AcademicTitleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
