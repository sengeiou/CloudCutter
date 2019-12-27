import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SetchilunbiPage } from './setchilunbi.page';

describe('SetchilunbiPage', () => {
  let component: SetchilunbiPage;
  let fixture: ComponentFixture<SetchilunbiPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetchilunbiPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SetchilunbiPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
