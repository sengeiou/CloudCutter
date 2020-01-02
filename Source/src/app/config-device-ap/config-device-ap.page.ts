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

  step = 1;
  wifiname = "";
  wifipassword = "";

  onMyLoad() {
    //参数
    this.params;
  }

  onMyShow() {

  }

  async tryapconnect() {

    const loading = await this.loadingCtrl.create({ message: "尝试连接", backdropDismiss: false });
    await loading.present();
    var socket = new TCPSocket("192.168.10.20", "5000");
    socket.TestOpen((res) => {
      loading.dismiss();
      if (res.status == true) {
        this.step = 1;
      } else {
        this.showAlert("检测未连接AP模式" + res.result);
      }
    });
  }

  async setSTAWIFI() {

    const loading = await this.loadingCtrl.create({ message: "开始设置", backdropDismiss: false });
    await loading.present();
    var socket = new TCPSocket("192.168.10.20", "5000");
    var sender = new Sender(socket);
    sender.setSTAInfo(this.wifiname, this.wifipassword, (ret) => {
      sender.close();
      this.showAlert("设置成功");
      loading.dismiss();
    }, () => {
      this.showAlert("设置成功");
      loading.dismiss();
    });
  }
}
