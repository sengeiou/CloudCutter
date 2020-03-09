import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { type } from 'os';
import { DeviceApi } from 'src/providers/device.api';

@Component({
  selector: 'app-setchilunbi',
  templateUrl: './setchilunbi.page.html',
  styleUrls: ['./setchilunbi.page.scss'],
  providers: [MemberApi, DeviceApi]
})
export class SetchilunbiPage extends AppBase {

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

  }
  value1 = 0;
  value2 = 0;
  onMyLoad() {
    //参数
    this.params;
    //this.value1 = this.params.x_axis;
    //this.value2 = this.params.y_axis;
  }

  device = null;
  online = false;
  onMyShow() {
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
  changes(e, name) {
    this.value1 = e.detail.value
    console.log(name, '触发', e);

    if(e.detail.value!=""&&e.detail.value!=null&&e.detail.value!=undefined){
      this.showConfirm(this.lang.qr+this.lang.xiugai, (ret) => {
        if (ret) {
          this.set(this.value1, 'P')
        }else{
          this.onMyShow();
        }
      })
    }
    
  }
  changes2(e, name) {
    this.value2 = e.detail.value
    console.log(name, '触发', e);

    if(e.detail.value!=""&&e.detail.value!=null&&e.detail.value!=undefined){
      this.showConfirm(this.lang.qr+this.lang.xiugai, (ret) => {
        if (ret) {
          this.set(this.value2, 'R')
        }else{
          this.onMyShow();
        }
      })
    }
    
  }
  set(value, types) {
    // return;  
    this.memberApi.setmorendaoya({
      type: types,
      id: this.memberInfo.id,
      axis: value
    }).then((ret) => {

    })

  }

  update() {
    this.sendTCP(this.device.deviceno, "GEAR", this.value1.toString() + "," + this.value2.toString(), (ret) => {
      var tcpret = ret.split("|");
      if (tcpret[0] == "OK") {
        this.toast("修改齿轮比成功");
      }
    });
  }
}

