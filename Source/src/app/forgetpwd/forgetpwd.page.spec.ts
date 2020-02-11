import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ForgetpwdPage } from './forgetpwd.page';

describe('ForgetpwdPage', () => {
  let component: ForgetpwdPage;
  let fixture: ComponentFixture<ForgetpwdPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForgetpwdPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ForgetpwdPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
