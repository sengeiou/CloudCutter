import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { OpinionPage } from './opinion.page';

describe('OpinionPage', () => {
  let component: OpinionPage;
  let fixture: ComponentFixture<OpinionPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpinionPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(OpinionPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
