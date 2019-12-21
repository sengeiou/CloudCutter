import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { UserinformationPage } from './userinformation.page';

describe('UserinformationPage', () => {
  let component: UserinformationPage;
  let fixture: ComponentFixture<UserinformationPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserinformationPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(UserinformationPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
