import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { NetworkInterface } from '@ionic-native/network-interface/ngx';
import { Sender } from 'src/DataMgr/Sender';

@Component({
  selector: 'app-config-device-sta',
  templateUrl: './config-device-sta.page.html',
  styleUrls: ['./config-device-sta.page.scss'],
})
export class ConfigDeviceSTAPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi,
    public network: NetworkInterface
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;

  }

  onMyLoad() {
    //参数
    this.params;
  }
  checkingdevice = 0;
  devicelist = [];
  account={device_deviceno:""};
  onMyShow() {
    this.checkingdevice = 0;
    this.devicelist = [];
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
      //TCPSocket.GetSocketList()
      this.account=account;
    });

    this.network.getWiFiIPAddress().then((wifiinfo) => {
      var ip = wifiinfo.ip;
      TCPSocket.GetSocketList(ip, 5000, (list) => {
        this.checkingdevice = 1;
        for (let item of list) {
          this.checkdevice(item);
        }

      });
    });
  }

  useit(machineid){
    this.memberApi.usermachine({machineid,user_id:this.user_id}).then((ret)=>{
      if(ret.code!=0){
        this.showAlert(ret.return);
      }else{
        this.showAlert("设置成功");
      }
    });
  }


  checkdevice(ipinfo) {

    var socket = new TCPSocket(ipinfo.ip, "5000");
    var sender = new Sender(socket);
    sender.readMachineStatus((machineres) => {
      var deviceinfo = { ipinfo: ipinfo, 
        machinestatus: machineres.machinestatus, machineid: machineres.machineid, 
        speed: "--", press: "--" };
      this.devicelist.push(deviceinfo);

    }, () => {
    });
  }
}
