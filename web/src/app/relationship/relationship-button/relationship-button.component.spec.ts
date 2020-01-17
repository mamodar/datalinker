import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelationshipButtonComponent } from './relationship-button.component';

describe('RelationshipButtonComponent', () => {
  let component: RelationshipButtonComponent;
  let fixture: ComponentFixture<RelationshipButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelationshipButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelationshipButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
