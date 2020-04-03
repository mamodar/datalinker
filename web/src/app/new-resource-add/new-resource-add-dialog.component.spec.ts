import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewResourceAddDialogComponent } from './new-resource-add-dialog.component';

describe('ResourceNewDialogComponent', () => {
  let component: NewResourceAddDialogComponent;
  let fixture: ComponentFixture<NewResourceAddDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewResourceAddDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewResourceAddDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
