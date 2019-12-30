import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { DebuggingListPage } from './debugging-list.page';

describe('DebuggingListPage', () => {
  let component: DebuggingListPage;
  let fixture: ComponentFixture<DebuggingListPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DebuggingListPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(DebuggingListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
