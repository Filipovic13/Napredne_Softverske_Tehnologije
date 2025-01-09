import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EducationTitleListComponent } from './education-title-list.component';

describe('EducationTitleListComponent', () => {
  let component: EducationTitleListComponent;
  let fixture: ComponentFixture<EducationTitleListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EducationTitleListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EducationTitleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
