import { Component, ViewChild } from '@angular/core';
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
    public memberApi: MemberApi
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;

  }

  step = 0;
  wifiname = "";
  wifipassword = "";

  onMyLoad() {
    //参数
    this.params;
  }

  onMyShow() {

  }

  loading = null;

  async tryapconnect() {

    this.loading = await this.loadingCtrl.create({ message: this.lang.changshi, backdropDismiss: false });
    await this.loading.present();



    var socket = new TCPSocket("192.168.10.20", "5000");
    socket.TestOpen((res) => {
      this.loading.dismiss();
      if (res.status == true) {
        this.step = 1;
      } else {
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
      sender.close();
      this.showAlert(this.lang.setok);
      this.loading2.dismiss();
    }, () => {
      this.showAlert(this.lang.setok);
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
