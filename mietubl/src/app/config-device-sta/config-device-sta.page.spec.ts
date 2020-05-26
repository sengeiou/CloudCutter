import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ConfigDeviceSTAPage } from './config-device-sta.page';

describe('ConfigDeviceSTAPage', () => {
  let component: ConfigDeviceSTAPage;
  let fixture: ComponentFixture<ConfigDeviceSTAPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigDeviceSTAPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ConfigDeviceSTAPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
