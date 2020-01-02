import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SetdaoyaPage } from './setdaoya.page';

describe('SetdaoyaPage', () => {
  let component: SetdaoyaPage;
  let fixture: ComponentFixture<SetdaoyaPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetdaoyaPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SetdaoyaPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
