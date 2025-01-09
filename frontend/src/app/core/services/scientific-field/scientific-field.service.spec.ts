import { TestBed } from '@angular/core/testing';

import { ScientificFieldService } from './scientific-field.service';

describe('ScientificFieldService', () => {
  let service: ScientificFieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScientificFieldService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
