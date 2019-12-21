import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { AboutnativePage } from './aboutnative.page';

describe('AboutnativePage', () => {
  let component: AboutnativePage;
  let fixture: ComponentFixture<AboutnativePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AboutnativePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(AboutnativePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
