import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewResourceListComponent } from './new-resource-list.component';

describe('ResourceNewListComponent', () => {
  let component: NewResourceListComponent;
  let fixture: ComponentFixture<NewResourceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewResourceListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewResourceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
