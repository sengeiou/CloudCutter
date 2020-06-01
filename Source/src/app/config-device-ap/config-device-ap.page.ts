import { Component, ViewChild, NgZone, ElementRef } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides, LoadingController } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { dismiss } from '@ionic/core/dist/types/utils/overlays';
import { Sender } from 'src/DataMgr/Sender';
import { OpenNativeSettings } from '@ionic-native/open-native-settings/ngx';


declare var WifiWizard2: any;


@Component({
  selector: 'app-config-device-ap',
  templateUrl: './config-device-ap.page.html',
  styleUrls: ['./config-device-ap.page.scss'],
})

export class ConfigDeviceAPPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public loadingCtrl: LoadingController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public ngZone: NgZone,
    public memberApi: MemberApi,
    public elementRef: ElementRef,
    public openNativeSettings: OpenNativeSettings
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.isLoginPage = true;
  }

  step = 0;
  wifiname = "";
  wifipassword = "";
  currentwifiname="";
  Interval=null;
  online=null;
  sbnum="";
  number=0;
  tanchuang=0;

  onMyLoad() {
    //参数
    this.params;  
    this.sbnum=this.params.deviceno_2;
  }

  onMyShow() {
    

    var ssid=window.sessionStorage.getItem("ssid");
    window.sessionStorage.removeItem("ssid");
    if(ssid!=null){
     this.wifiname=ssid;
    }
 
    this.getWifiName();
    console.log("不查");

    

  }


  yanzheng(){
    
     this.number++
     console.log('查')
  
      this.sendTCP(this.params.deviceno_1, "SYNCSTATUS", "", (ret) => {
 
        var tcpret = ret.split("|");

        this.online = tcpret[0] == "OK";
  
        if(this.online==true){
           this.step=3;
        }else{
          if(this.number>30){
            this.step=4;
          }else{
            setTimeout(() => { 
            this.yanzheng();
            }, 1000);
          }
        }

      });
       
  }





  show(){
    this.tanchuang=1;
  }
  closetanchuang(){
    this.tanchuang=0;
  }
  wifinameinterval=null;

  getWifiName(){
    this.wifinameinterval=setInterval(()=>{
      WifiWizard2.getConnectedSSID().then((ret) => {
        //alert(JSON.stringify(ret));
        if(ret!=this.currentwifiname){
          this.currentwifiname=ret;
        }
      });
    },1000);
  }



  loading = null;

  async tryapconnect(num) {

   // console.log(num, '破婆婆空间');
  //  return;
  //  console.log(this.elementRef, '破婆婆空间');
   if(num==0){
     this.wifiname="";
     this.wifipassword="";
     this.currentwifiname="";
     this.number=0;
     this.Interval=null;
     this.online=null;
    this.onMyShow();
   }

    this.step = num;

    // this.loading = await this.loadingCtrl.create({ message: this.lang.changshi, backdropDismiss: false });
    // await this.loading.present();
 
    // var socket = new TCPSocket("192.168.10.20", "5000");
    // socket.TestOpen((res) => {
    //   if (res.status == true) {
    //     this.step = 1;
    //     (this.ngZone).run(() => {});
    //     this.loading.dismiss();
    //   } else {
    //     this.loading.dismiss();
    //     this.showAlert(this.lang.jiance);
    //   }
    // });

  }

  gotosetting() {

    this.openNativeSettings.open("wifi").then((ret) => {
      //alert(JSON.stringify(ret));
    })
  }

  


  loading2 = null;
  async setSTAWIFI() {
    // this.loading2 = await this.loadingCtrl.create({ message: this.lang.startset, backdropDismiss: false });
    // await this.loading2.present();
    var socket = new TCPSocket("192.168.10.20", "5000");
    var sender = new Sender(socket);
    sender.setSTAInfo(this.wifiname, this.wifipassword, (ret) => {

      //alert("SETFAIL");
      //alert(ret.hint);
      //this.loading2.dismiss();

     // if (ret.resultcode == 0) {
        //this.showAlert(this.lang.setok);
      //  this.step = 3;
      //} else {
        //alert("设置失败");
        //this.step = 4;
      //}

      this.step = 5;
       this.yanzheng();
     // this.loading2.dismiss();
     // sender.close();
    }, () => {
      //this.showAlert(this.lang.setok);
      //alert("设置失败");
      this.yanzheng();
      this.step = 5;
    //  this.loading2.dismiss();
    });
  }




  onMyUnload() {
    clearInterval(this.wifinameinterval);
    if (this.loading != null) {
      try {
        this.loading.dismiss();
      } catch (e) {
      }
    }
    if (this.loading2 != null) {
      try {
        this.loading2.dismiss();
      } catch (e) {
      }
    }
  }





}
