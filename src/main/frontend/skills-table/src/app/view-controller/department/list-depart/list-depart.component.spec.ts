import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListDepartComponent } from './list-depart.component';

describe('ListDepartComponent', () => {
  let component: ListDepartComponent;
  let fixture: ComponentFixture<ListDepartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListDepartComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListDepartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
