import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourceDeleteButtonComponent } from './resource-delete-button.component';

describe('ResourceDeleteButtonComponent', () => {
  let component: ResourceDeleteButtonComponent;
  let fixture: ComponentFixture<ResourceDeleteButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourceDeleteButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceDeleteButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
