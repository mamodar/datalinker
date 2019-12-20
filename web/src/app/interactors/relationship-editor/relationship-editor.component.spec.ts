import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RelationshipEditorComponent} from './relationship-editor.component';

describe('RelationshipEditorComponent', () => {
  let component: RelationshipEditorComponent;
  let fixture: ComponentFixture<RelationshipEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RelationshipEditorComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelationshipEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
