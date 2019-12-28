import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { DebuggingPage } from './debugging.page';

describe('DebuggingPage', () => {
  let component: DebuggingPage;
  let fixture: ComponentFixture<DebuggingPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DebuggingPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(DebuggingPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
