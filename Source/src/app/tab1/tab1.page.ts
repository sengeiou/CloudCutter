import { Component, ViewChild, ElementRef } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides, IonInfiniteScroll, IonMenu } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
//import { PhoneApi } from 'src/providers/phone.api';
import {PhoneApi} from 'src/providers/phone.api';
import { AppComponent } from '../app.component';
import { TabsPage } from '../tabs/tabs.page';
@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
  providers:[MemberApi,PhoneApi]
})
export class Tab1Page extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public memberApi: MemberApi,
    public phoneapi:PhoneApi,
    public activeRoute: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private elementRef: ElementRef
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.currentpage = "tab1";
    
    // AppBase.TABName = "tab1";
    // AppBase.LASTTAB = this;
  }
  checks='A';
  modellist=[];
  onMyShow(){
    AppBase.TABName = "tab1";
    AppBase.LASTTAB = this;
    
    this.phoneapi.modellist({}).then((modellist:any)=>{
      this.modellist= modellist;
      console.log(this.modellist,'快快快')
    })

  }

 check(checks){
   console.log(checks);
    this.checks=checks;
 }

}