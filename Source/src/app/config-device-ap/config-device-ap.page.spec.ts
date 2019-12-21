import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ConfigDeviceAPPage } from './config-device-ap.page';

describe('ConfigDeviceAPPage', () => {
  let component: ConfigDeviceAPPage;
  let fixture: ComponentFixture<ConfigDeviceAPPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigDeviceAPPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ConfigDeviceAPPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
