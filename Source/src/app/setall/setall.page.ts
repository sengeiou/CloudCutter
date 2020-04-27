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
  selector: 'app-setall',
  templateUrl: './setall.page.html',
  styleUrls: ['./setall.page.scss'],
  providers: [MemberApi, DeviceApi]
})
export class SetallPage  extends AppBase {

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
  show = false;
  sudu = 0;
  gearratio = 0;
  values = 0;
  neiron = ''; 
  isok = false;
  device = null;
  online = false;
  onMyLoad(){
    //参数
    this.params;
    this.show == false;
    //this.sudu = this.params.sudu;
 
    window.localStorage.removeItem("width");
    window.localStorage.removeItem("spacing");
    window.localStorage.removeItem("gear");
    
    this.user_id = window.localStorage.getItem("user_id");
    
    console.log(this.user_id,'ooo');

    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
 
      console.log(this.user_id,account,'ppp');
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
 
  onMyShow(){
   this.show == false;
  
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

  chongzhi() {
    //this.show = true;
    //this.showAlert("重置模式决定了没？我建议放到机器按钮，不要在app搞这个");
    //this.device.spacing = e.detail.checked == true ? 1 : 0;
    //alert(this.device.spacing);
    
    this.showConfirm(this.lang.querencz, (ret) => {
      if (ret) {
 
        this.sendTCP(this.device.deviceno, "RESET", "2", (ret) => {
          // alert(ret);
          
          var tcpret = ret.split("|");
          if (tcpret[0] == "OK") {
            
            this.memberApi.reset({ account_id: this.memberInfo.id }).then((ret) => {
              this.onMyShow();
            });

            this.toast(this.lang.czok);
          }
        });
         
      }
    })

  }

  setkaiguan(check) {
    this.showConfirm(this.lang.qr+this.lang.xiugai, (ret) => {
      if (ret) {
       this.navigate("/set", {  leixin: 2 ,check:check});
      }
    })
  }

  setchilun(gr) {
    this.navigate("/setchilunbi", { gr:gr });
  }

  setfukuan(fk) {
    this.showConfirm(this.lang.qr+this.lang.xiugai, (ret) => {
      if (ret) {
    this.navigate("/set", {  leixin: 1,fk:fk,check:0 });
      }
    })
  }

  sets(){
    this.show = true; 
  }
}
