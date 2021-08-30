import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormTeamComponent } from './form-team.component';

describe('AddDepartComponent', () => {
  let component: FormTeamComponent;
  let fixture: ComponentFixture<FormTeamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormTeamComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormTeamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
