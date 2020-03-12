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
  width=null;
  spacing=null;
  gear=null;
  onMyLoad() {
    //参数
    this.params;
    this.show == false;
    this.sudu = this.params.sudu;
 
    window.localStorage.removeItem("width");
    window.localStorage.removeItem("spacing");
    window.localStorage.removeItem("gear");
    
    this.user_id = window.localStorage.getItem("user_id");
    
    console.log(this.user_id,'ooo')

    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
 
      console.log(this.user_id,account,'ppp')
      this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
         
        // if( window.sessionStorage.getItem("XY")!=null){
        //   device.gear= window.sessionStorage.getItem("XY");
        // }

        this.device = device;

        //window.localStorage.removeItem("isregister");
        //window.localStorage.getItem("UserToken")

        window.localStorage.setItem("width",this.device.width);
        window.localStorage.setItem("spacing",this.device.spacing);
        window.localStorage.setItem("gear",this.device.gear);
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

     this.width=window.localStorage.getItem("width");
     console.log(this.width,'aa4444',window.localStorage.getItem("width"));
    this.spacing=window.localStorage.getItem("spacing");
    this.gear=window.localStorage.getItem("gear");
 
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
  changesudu(e, name) {
    this.values = e.detail.value;

    this.memberApi.setmorendaoya({
      type: 'Q',
      id: this.memberInfo.id,
      sudu: this.values
    }).then((ret) => {
      // console.log(ret)
      this.sendTCP(this.memberInfo.device_deviceno, "SPEED", e.detail.value, (ret3) => {});
    })
    console.log(name, '触发', e)
  }
   

  clickxianwei() {
    if (this.isok == false) {
      this.show = true;
    } else {
      this.show = false;
    }
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

  setkaiguan(check) {
    this.showConfirm(this.lang.qr+this.lang.xiugai, (ret) => {
      if (ret) {
       this.navigate("/set", {  leixin: 2 ,check:check});
      }
    })
  }

  tishi(){
    this.showConfirm(this.lang.querencz, (ret) => {
      if (ret) {}
    })
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
            this.toast(this.lang.czok);
          }
        });
      }
    })

  }

  close() {
    this.show = false;
  }



}
