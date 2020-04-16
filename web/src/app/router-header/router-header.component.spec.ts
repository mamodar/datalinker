import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RouterHeaderComponent } from './router-header.component';

describe('RouterHeaderComponent', () => {
  let component: RouterHeaderComponent;
  let fixture: ComponentFixture<RouterHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RouterHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RouterHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
