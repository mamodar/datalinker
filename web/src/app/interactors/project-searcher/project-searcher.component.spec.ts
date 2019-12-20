import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ProjectSearcherComponent} from './project-searcher.component';

describe('ProjectFieldComponent', () => {
  let component: ProjectSearcherComponent;
  let fixture: ComponentFixture<ProjectSearcherComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectSearcherComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectSearcherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
