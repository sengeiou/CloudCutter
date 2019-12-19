import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { LanguagesettingsPage } from './languagesettings.page';

describe('LanguagesettingsPage', () => {
  let component: LanguagesettingsPage;
  let fixture: ComponentFixture<LanguagesettingsPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LanguagesettingsPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(LanguagesettingsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
