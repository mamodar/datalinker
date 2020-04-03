import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewResourceAttachButtonComponent } from './new-resource-attach-button.component';

describe('ResourceNewAttachButtonComponent', () => {
  let component: NewResourceAttachButtonComponent;
  let fixture: ComponentFixture<NewResourceAttachButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewResourceAttachButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewResourceAttachButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
