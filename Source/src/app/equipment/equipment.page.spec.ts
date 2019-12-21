import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { EquipmentPage } from './equipment.page';

describe('EquipmentPage', () => {
  let component: EquipmentPage;
  let fixture: ComponentFixture<EquipmentPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EquipmentPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(EquipmentPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
