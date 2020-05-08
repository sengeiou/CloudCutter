import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { DeviceApi } from 'src/providers/device.api';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.page.html',
  styleUrls: ['./setting.page.scss'],
  providers: [MemberApi, DeviceApi]
})
export class SettingPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi,
    public deviceApi: DeviceApi
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.device={};
  }

  sudu = 0;
  gearratio = 0;
  values = 0;
  neiron = '';
  show = false;
  isok = false;
  device = null;
  online = false;
  zhi=0;
  onMyLoad() {
    //参数
    this.params;
    this.show == false;
    //this.sudu = this.params.sudu;
 
    window.localStorage.removeItem("width");
    window.localStorage.removeItem("spacing");
    window.localStorage.removeItem("gear");
    
    this.user_id = window.localStorage.getItem("user_id");
    
    console.log(this.user_id,'ooo')

    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
 
      console.log(this.user_id,account,'ppp')
      this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
        this.device = device;
        //this.sudu=device.sudu;
        console.log(device);
      });

      this.sendTCP(account.device_deviceno, "SYNCSTATUS", "", (ret) => {
        var tcpret = ret.split("|");
        this.online = tcpret[0] == "OK";

        setTimeout(() => {
          this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
            this.device = device;
          });
        }, 1000);
      });

    });

  }
   
  onMyShow() {
    this.show == false;

    // this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
 
    //   this.memberInfo.daoya=account.daoya1;
    //   this.memberInfo.daoyaname1=account.daoyaname1;
    // });

     //this.width=window.localStorage.getItem("width");
     //console.log(this.width,'aa4444',window.localStorage.getItem("width"));
    //this.spacing=window.localStorage.getItem("spacing");
    //this.gear=window.localStorage.getItem("gear");

    var cwidth=window.sessionStorage.getItem("width");
    if(cwidth!=null){
      this.device.width=cwidth;
    }
    var cspacing=window.sessionStorage.getItem("spacing");
    if(cspacing!=null){
      this.device.spacing=cspacing;
    }
    var cgear=window.sessionStorage.getItem("gear");
    if(cgear!=null){
      this.device.gear=cgear;
    }
    window.sessionStorage.removeItem("width");
    window.sessionStorage.removeItem("spacing");
    window.sessionStorage.removeItem("gear");
  }

  setdaoya(checking, daoya1, daoya2, daoya3, daoya4, daoya5) {
    this.navigate("/setdaoya", { id: checking, daoya1: daoya1, daoya2: daoya2, daoya3: daoya3, daoya4: daoya4, daoya5: daoya5 })
  }

  click(type) {
    console.log(type, '类型')
  }

  set(e) {
    console.log(e.key, '略略略', this.sudu, '咳咳咳', this.gearratio)
  }
  sets(){
    this.show = true;
 
  }
 
  update(){
    console.log('梵蒂冈',this.zhi);

    //  if(this.zhi>500){
    //   this.zhi=500;
    //   this.nobackshowAlert(this.lang.chaochu);
    //  }

     this.memberApi.setmorendaoya({
      type: 'Q',
      id: this.memberInfo.id,
      sudu: this.zhi
    }).then((ret) => {
      // console.log(ret)
      this.sendTCP(this.memberInfo.device_deviceno, "SPEED", this.zhi, (ret3) => {});
    })
     
  }
  

  
  // changesudu(e, name) { 
  //   this.values = e.detail.value;
    
  //   if(this.values>500){
  //      console.log('STOP');
  //      //this.nobackshowAlert('超出范围')
  //      this.values=500;  
  //   }

  //   this.memberApi.setmorendaoya({
  //     type: 'Q',
  //     id: this.memberInfo.id,
  //     sudu: this.values
  //   }).then((ret) => {
  //     // console.log(ret)
  //     this.sendTCP(this.memberInfo.device_deviceno, "SPEED", e.detail.value, (ret3) => {});
  //   })
  //   console.log(name, '触发', e)
  // }

  
  submit(account,sudu,xianwei,checking,daoya) {

    // console.log(this.neiron,account)
    //return;

    console.log(this.memberInfo)
    this.memberApi.checkpws({
      account_id:this.user_id,
      password: this.neiron
    }).then((ret) => {
      if (ret.code == "0") {
        console.log(ret);
        this.show = false;
        this.neiron = '';
        this.navigate("setall", { sudu:sudu,xianwei:xianwei,checking:checking,daoya:daoya });
      } else {
        this.toast(this.lang.mimacuo);
        return;
      }
    })
 
  }
    
 

  tishi(){
    this.showConfirm(this.lang.querencz, (ret) => {
      if (ret) {}
    })
  }
 

  close() {
    this.show = false;
  }



}
