import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonInformationComponent } from './button-information.component';

describe('ButtonInformationComponent', () => {
  let component: ButtonInformationComponent;
  let fixture: ComponentFixture<ButtonInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ButtonInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ButtonInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
