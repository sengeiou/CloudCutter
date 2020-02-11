import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { AuthloginPage } from './authlogin.page';

describe('AuthloginPage', () => {
  let component: AuthloginPage;
  let fixture: ComponentFixture<AuthloginPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthloginPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(AuthloginPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
