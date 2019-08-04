import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarCustomComponent } from './navbar-custom.component';

describe('NavbarCustomComponent', () => {
  let component: NavbarCustomComponent;
  let fixture: ComponentFixture<NavbarCustomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavbarCustomComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarCustomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
