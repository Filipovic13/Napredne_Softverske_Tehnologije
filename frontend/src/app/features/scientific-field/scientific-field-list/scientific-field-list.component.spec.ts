import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientificFieldListComponent } from './scientific-field-list.component';

describe('ScientificFieldListComponent', () => {
  let component: ScientificFieldListComponent;
  let fixture: ComponentFixture<ScientificFieldListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScientificFieldListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScientificFieldListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
