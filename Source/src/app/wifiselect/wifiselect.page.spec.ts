import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { WifiselectPage } from './wifiselect.page';

describe('WifiselectPage', () => {
  let component: WifiselectPage;
  let fixture: ComponentFixture<WifiselectPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WifiselectPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(WifiselectPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
