import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { fail } from 'assert';

@Component({
  selector: 'app-setwifi',
  templateUrl: './setwifi.page.html',
  styleUrls: ['./setwifi.page.scss'],
  providers:[MemberApi]
})
export class SetwifiPage  extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi:MemberApi
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480; 

  }
  
  onMyLoad(){
    //参数
    this.params;
  }
 
  onMyShow(){
 
  }
   
  set(){
   
    console.log('下一步');
    
  //  let options=[
  //    {
  //     "level": '', 
  //     "SSID": '', 
  //     "BSSID": '',
  //     "frequency": '',
  //     "capabilities": ''
  //    }
  //  ]
    
  }
  startScan( ){
    
  }

  getScanResults([options], listHandler, fail){

  }


}
 
