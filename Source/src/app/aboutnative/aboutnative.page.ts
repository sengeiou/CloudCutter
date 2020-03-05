import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { DeviceApi } from 'src/providers/device.api';

@Component({
  selector: 'app-aboutnative',
  templateUrl: './aboutnative.page.html',
  styleUrls: ['./aboutnative.page.scss'],
  providers:[MemberApi,DeviceApi]
})
export class AboutnativePage  extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi:MemberApi,
    public deviceApi: DeviceApi
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480; 
      this.device={};
  }
  xieyi=[];
  device=null;
  account = null;
  onMyLoad(){
    //参数
    this.params;
  }
 
  onMyShow(){

console.log(this.user_id,'有没有');
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
    
      this.deviceApi.info({  deviceno : account.device_deviceno }).then((device) => {
        this.device = device;
        console.log(device,'来来来');
      });
 
    });


    // this.memberApi.xieyi({ }).then((xieyi: any) => { 
    //   this.xieyi=xieyi;
    //   console.log(xieyi)
    // })
  }
}
 
