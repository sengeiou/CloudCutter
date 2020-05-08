import { Component, ViewChild, NgZone } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
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
export class SetPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi,
    public deviceApi: DeviceApi,
    public ngzone: NgZone
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;

  }

  leixin = null;
  device = null;
  fk = null;
  check = null;
  value1 = 0;
  value2 = 0;
  online = false;
  fukuan = 0;
  onMyLoad() {

    this.params;

  }

  onMyShow() {

    this.leixin = this.params.leixin;
    this.fk = this.params.fk;
    this.check = this.params.check;
    this.ngzone.run(() => { });

    console.log(this.leixin, '--', this.fk, '--', this.check)
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {

      this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
        this.device = device;

      });
      // alert(account.device_deviceno)
      this.sendTCP(account.device_deviceno, "SYNCSTATUS", "", (ret) => {
        var tcpret = ret.split("|");
        this.online = tcpret[0] == "OK";
      });

    });
  }

  changefukuan() {
    // if (this.fukuan > 1300) {
    //   this.fukuan = 1300;
    //   this.nobackshowAlert(this.lang.chaochu);
    // }
    window.sessionStorage.setItem("width",this.fukuan.toString());
    this.sendTCP(this.device.deviceno, "WIDTH", this.fukuan, (ret) => {
    });
  }


  changexianwei(e) {
    this.device.spacing = e.detail.checked == true ? 1 : 0;
    window.sessionStorage.setItem("spacing", this.device.spacing);
    //alert(this.device.spacing); 
    this.sendTCP(this.device.deviceno, "SPACING", this.device.spacing, (ret) => {
    });

  }

}