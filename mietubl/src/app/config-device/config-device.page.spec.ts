import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ConfigDevicePage } from './config-device.page';

describe('ConfigDevicePage', () => {
  let component: ConfigDevicePage;
  let fixture: ComponentFixture<ConfigDevicePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigDevicePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ConfigDevicePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
