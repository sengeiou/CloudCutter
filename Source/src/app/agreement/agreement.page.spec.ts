import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { AgreementPage } from './agreement.page';

describe('AgreementPage', () => {
  let component: AgreementPage;
  let fixture: ComponentFixture<AgreementPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgreementPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(AgreementPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
