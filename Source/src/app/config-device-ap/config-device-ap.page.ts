import { Component, ViewChild, NgZone } from '@angular/core';
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
    public memberApi: MemberApi
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.isLoginPage=true;
  }

  step = 0;
  wifiname = "";
  wifipassword = "";

  onMyLoad() {
    //参数
    this.params;
  }

  onMyShow() {
    var ssid=window.sessionStorage.getItem("ssid");
    window.sessionStorage.removeItem("ssid");
    if(ssid!=null){
      this.wifiname=ssid;
    }
  }

  loading = null;

  async tryapconnect() {

    this.loading = await this.loadingCtrl.create({ message: this.lang.changshi, backdropDismiss: false });
    await this.loading.present();



    var socket = new TCPSocket("192.168.10.20", "5000");
    socket.TestOpen((res) => {
      if (res.status == true) {
        this.step = 1;
        (this.ngZone).run(() => { });
        this.loading.dismiss();
      } else {
        this.loading.dismiss();
        this.showAlert(this.lang.jiance);
      }
    });
  }
  loading2 = null;
  async setSTAWIFI() {

    this.loading2 = await this.loadingCtrl.create({ message: this.lang.startset, backdropDismiss: false });
    await this.loading2.present();
    var socket = new TCPSocket("192.168.10.20", "5000");
    var sender = new Sender(socket);
    sender.setSTAInfo(this.wifiname, this.wifipassword, (ret) => {
      //alert("SETFAIL");
      //alert(ret.hint);
      this.loading2.dismiss();
      if(ret.resultcode==0){
        this.showAlert(this.lang.toast);
      }else{
        //alert("设置失败");
      }
      sender.close();
      this.loading2.dismiss();
    }, () => {
      //this.showAlert(this.lang.setok);
      //alert("设置失败");
      this.loading2.dismiss();
    });
  }

  onMyUnload() {
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
