import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormDepartComponent } from './form-depart.component';

describe('AddDepartComponent', () => {
  let component: FormDepartComponent;
  let fixture: ComponentFixture<FormDepartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormDepartComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormDepartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
