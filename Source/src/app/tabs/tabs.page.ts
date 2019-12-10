import { Component } from '@angular/core';
import { AppBase } from '../AppBase';

@Component({
  selector: 'app-tabs',
  templateUrl: 'tabs.page.html',
  styleUrls: ['tabs.page.scss']
})
export class TabsPage {

  static Instance:TabsPage=null;
  currentpage="tab1";
  constructor() {
  }
  hidetab=false;
  xz=1;
  count=0;
  ionViewDidEnter() {

    
    TabsPage.Instance=this;
    
    if (AppBase.LASTTAB != null) {
      AppBase.LASTTAB.ionViewDidEnter();
    }

  }

}
