import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QrcodescanPage } from './qrcodescan.page';

describe('QrcodescanPage', () => {
  let component: QrcodescanPage;
  let fixture: ComponentFixture<QrcodescanPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QrcodescanPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QrcodescanPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
