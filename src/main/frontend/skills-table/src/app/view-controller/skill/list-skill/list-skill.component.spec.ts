import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSkillComponent } from './list-skill.component';

describe('ListDepartComponent', () => {
  let component: ListSkillComponent;
  let fixture: ComponentFixture<ListSkillComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListSkillComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListSkillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
