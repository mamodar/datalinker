import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewResourceAddButtonComponent } from './new-resource-add-button.component';

describe('ResourceNewComponent', () => {
  let component: NewResourceAddButtonComponent;
  let fixture: ComponentFixture<NewResourceAddButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewResourceAddButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewResourceAddButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
