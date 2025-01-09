import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcademicTitleListComponent } from './academic-title-list.component';

describe('AcademicTitleListComponent', () => {
  let component: AcademicTitleListComponent;
  let fixture: ComponentFixture<AcademicTitleListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AcademicTitleListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AcademicTitleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
