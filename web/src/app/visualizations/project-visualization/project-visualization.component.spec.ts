import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ProjectVisualizationComponent} from './project-visualization.component';

describe('ProjectVisualizationComponent', () => {
  let component: ProjectVisualizationComponent;
  let fixture: ComponentFixture<ProjectVisualizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectVisualizationComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectVisualizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
