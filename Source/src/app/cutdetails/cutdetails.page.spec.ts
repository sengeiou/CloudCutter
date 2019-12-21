import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CutdetailsPage } from './cutdetails.page';

describe('CutdetailsPage', () => {
  let component: CutdetailsPage;
  let fixture: ComponentFixture<CutdetailsPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CutdetailsPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CutdetailsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
