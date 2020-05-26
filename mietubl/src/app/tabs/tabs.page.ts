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
  tab1=null;
  tab2=null;
  tab3=null;
  tab4=null;
  ionViewDidEnter() {

    
    TabsPage.Instance=this;
    console.log(TabsPage.Instance,'歷史')
    
    if (AppBase.LASTTAB != null) {
      AppBase.LASTTAB.ionViewDidEnter();
    }

  }

}
