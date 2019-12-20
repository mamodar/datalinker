import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ResourceVisualizationComponent} from './resource-visualization.component';

describe('ResourceVisualizationComponent', () => {
  let component: ResourceVisualizationComponent;
  let fixture: ComponentFixture<ResourceVisualizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ResourceVisualizationComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceVisualizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
