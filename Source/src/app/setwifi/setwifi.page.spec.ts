import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SetwifiPage } from './setwifi.page';

describe('SetwifiPage', () => {
  let component: SetwifiPage;
  let fixture: ComponentFixture<SetwifiPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetwifiPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SetwifiPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
