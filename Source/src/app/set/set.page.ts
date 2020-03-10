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
  selector: 'app-set',
  templateUrl: './set.page.html',
  styleUrls: ['./set.page.scss'], 
  providers: [MemberApi, DeviceApi]
})
export class SetPage  extends AppBase {

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

  }
 
  type=null;
  device = null; 
  value1 = 0;
  value2 = 0;
  online = false;
  onMyLoad(){
    //参数
    this.params;

    this.type=this.params.types; 
  }
 
  onMyShow(){
  this.memberApi.accountinfo({ id: this.user_id }).then((account) => {

      this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
        this.device = device;
        var gear = this.device.gear;
        var geararr = gear.split(",");
        this.value1 = parseInt(geararr[0]);
        this.value2 = parseInt(geararr[1]);
      });

      this.sendTCP(account.device_deviceno, "SYNCSTATUS", "", (ret) => {
        var tcpret = ret.split("|");
        this.online = tcpret[0] == "OK";
      });

    });
  }

  changefukuan(e) { 
    //e.detail.value;
   console.log(e.detail.value,'幅宽'); 
         this.sendTCP(this.device.deviceno, "WIDTH", e.detail.value, (ret) => {  
     }) 
 }


 changexianwei(e) {
  this.device.spacing = e.detail.checked == true ? 1 : 0;
  //alert(this.device.spacing);
 
  this.sendTCP(this.device.deviceno, "SPACING", this.device.spacing, (ret) => { 
  });
     
}

}