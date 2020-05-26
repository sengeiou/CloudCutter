import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ChoosemodelPage } from './choosemodel.page';

describe('ChoosemodelPage', () => {
  let component: ChoosemodelPage;
  let fixture: ComponentFixture<ChoosemodelPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChoosemodelPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ChoosemodelPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
