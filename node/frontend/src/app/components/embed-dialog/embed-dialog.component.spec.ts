import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmbedDialogComponent } from './embed-dialog.component';

describe('EmbedDialogComponent', () => {
  let component: EmbedDialogComponent;
  let fixture: ComponentFixture<EmbedDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmbedDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmbedDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
