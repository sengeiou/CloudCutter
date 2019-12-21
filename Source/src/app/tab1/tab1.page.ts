import { Component, ViewChild, ElementRef } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides, IonInfiniteScroll, IonMenu, LoadingController } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
//import { PhoneApi } from 'src/providers/phone.api';
import { PhoneApi } from 'src/providers/phone.api';
import { AppComponent } from '../app.component';
import { TabsPage } from '../tabs/tabs.page';
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { NetworkInterface } from '@ionic-native/network-interface/ngx';
import { Sender } from 'src/DataMgr/Sender';
import { isNgTemplate } from '@angular/compiler';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';


@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
  providers: [MemberApi, PhoneApi]
})
export class Tab1Page extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public loadingCtrl: LoadingController,
    public memberApi: MemberApi,
    public phoneapi: PhoneApi,
    public activeRoute: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private elementRef: ElementRef,
    public network: NetworkInterface
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.currentpage = "tab1";

    // AppBase.TABName = "tab1";
    // AppBase.LASTTAB = this;
  }

  //检查设备，0未绑定，1检查中，2已获得，3无设备
  checkingdevice = 0;
  deviceinfo = null;
  devicelist = [];

  checks = 'A';
  modellist = [];
  onMyShow() {
    AppBase.TABName = "tab1";
    AppBase.LASTTAB = this;

    this.phoneapi.modellist({}).then((modellist: any) => {
      this.modellist = modellist;
      console.log(this.modellist, '快快快')
    });


    this.checkingdevice = 0;
    this.devicelist = [];
    this.deviceinfo=null;
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
      this.network.getWiFiIPAddress().then((wifiinfo) => {
        var ip = wifiinfo.ip;
        TCPSocket.GetSocketList(ip, 5000, (list) => {
          this.checkingdevice=1;
          for (let item of list) {
            this.checkdevice(item, account);
          }
          
        });
      });
      //TCPSocket.GetSocketList()
    });




  }

  checkdevice(ipinfo, account) {

    var deviceno = account.device_deviceno;
    var socket = new TCPSocket(ipinfo.ip, "5000");
    var sender = new Sender(socket);
    sender.readMachineStatus((machineres) => {
      if (account.power == "A") {
        if (deviceno != machineres.machineid) {
          //测试的原因
          //return;
        }
      }
      this.checkingdevice=2;
      var deviceinfo = {ipinfo:ipinfo, machinestatus: machineres.machinestatus, machineid: machineres.machineid, speed: "--", press: "--" };
      this.deviceinfo=deviceinfo;
      this.devicelist.push(deviceinfo);
      sender.readSpeed((speedres) => {
        deviceinfo.speed = speedres.speed;
        this.deviceinfo=deviceinfo;
        sender.readBladePressure((pressres)=>{
          this.deviceinfo.press=pressres.press;
          sender.close();
        },()=>{})
      }, () => {
      })

    }, () => {
    });
  }

  check(checks) {
    console.log(checks);
    this.checks = checks;
  }
  todetails(id) {
    this.navigate("/cutdetails", { id: id })
  }

  async trycut(){

    const loading=await this.loadingCtrl.create({message:"微信登录中",backdropDismiss:false});
    await loading.present();

    var socket=new TCPSocket(this.deviceinfo.ipinfo.ip,"5000");
    var sender=new Sender(socket);
    sender.tryCuy((ret)=>{
      loading.dismiss();
      this.toast("试刻成功");
    },(err)=>{
      loading.dismiss();
      this.showAlert("试刻遇到问题了："+JSON.stringify(err));
    });
  }

}
