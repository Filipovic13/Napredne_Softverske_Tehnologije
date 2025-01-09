import { TestBed } from '@angular/core/testing';

import { ManagementOfDepartmentHistorService } from './management-of-department-histor.service';

describe('ManagementOfDepartmentHistorService', () => {
  let service: ManagementOfDepartmentHistorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManagementOfDepartmentHistorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
