import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { PhoneApi } from 'src/providers/phone.api';
import { AppComponent } from '../app.component';


@Component({
  selector: 'app-wifiselect',
  templateUrl: './wifiselect.page.html',
  styleUrls: ['./wifiselect.page.scss'],
})
export class WifiselectPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.isLoginPage = true;
  }



  onMyLoad() {
    //参数
    this.params;
  }

  list = [];

  onMyShow() {
    var cordova = null;
    cordova = window.cordova.plugins;
    let wifiManager = cordova.WifiManagerPlugin;
    wifiManager.startWifiScan((ret) => {
      console.log("startWifiScan", ret);
      wifiManager.getAvailableNetworksList((list) => {
        console.log("getAvailableNetworksList", list);
        this.list = list;
      }, (err2) => {

        console.log("getAvailableNetworksList err", err2);
      }, true);
    }, (err) => {
      console.log("startWifiScan err", err);
      this.toast(this.lang.wifipermission);
    });
  }
  returnconn(ssid) {
    window.sessionStorage.setItem("ssid", ssid);
    this.back();
  }

}

