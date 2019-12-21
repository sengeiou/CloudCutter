import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SetexplainPage } from './setexplain.page';

describe('SetexplainPage', () => {
  let component: SetexplainPage;
  let fixture: ComponentFixture<SetexplainPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetexplainPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SetexplainPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
