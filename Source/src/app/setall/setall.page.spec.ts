import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SetallPage } from './setall.page';

describe('SetallPage', () => {
  let component: SetallPage;
  let fixture: ComponentFixture<SetallPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetallPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SetallPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
