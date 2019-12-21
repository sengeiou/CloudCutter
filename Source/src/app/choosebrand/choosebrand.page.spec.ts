import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ChoosebrandPage } from './choosebrand.page';

describe('ChoosebrandPage', () => {
  let component: ChoosebrandPage;
  let fixture: ComponentFixture<ChoosebrandPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChoosebrandPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ChoosebrandPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
