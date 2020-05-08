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
  device = null;
  online = false;
  setting=false;
  tcpret="";
  gear=0;
  value1 = 0;
  value2 = 0;
  xzhi=0;
  yzhi=0;
  onMyLoad() {
    //参数
    this.params;
    var gear=this.params.gr;
    var geararr = gear.split(",");
    this.value1 = parseInt(geararr[0]);
    this.value2 = parseInt(geararr[1]); 
  }
 
  onMyShow() {
    //console.log('1')
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
     // console.log('2')
      this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
        this.device = device; 
      
      });
      
      this.sendTCP(account.device_deviceno, "SYNCSTATUS", "", (ret) => {
       
        var tcpret = ret.split("|");
        this.tcpret=ret;
        this.online = tcpret[0] == "OK";
      });

    });
  }
  updatexzhou() { 
    // if(this.xzhi>2000){
    //  this.xzhi=2000;
    //  this.nobackshowAlert(this.lang.chaochu);
    // }
    this.set(this.xzhi, 'P') 
  }
  updateyzhou() {
    // if(this.yzhi>2000){
    //   this.yzhi=2000;
    //   this.nobackshowAlert(this.lang.chaochu);
    //  }
    this.set(this.yzhi, 'R')  
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

    window.sessionStorage.setItem("gear",this.xzhi.toString() + "," + this.yzhi.toString());
    this.sendTCP(this.device.deviceno, "GEAR", this.xzhi.toString() + "," + this.yzhi.toString(), (ret) => {
      var tcpret = ret.split("|");
      if (tcpret[0] == "OK") {
        //this.toast("修改齿轮比成功");
      }
    });
  }

}

