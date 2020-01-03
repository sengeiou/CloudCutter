import { Component, ViewChild, ElementRef, NgZone } from '@angular/core';
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
import { DeviceApi } from 'src/providers/device.api';


@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
  providers: [MemberApi, PhoneApi, DeviceApi]
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
    public network: NetworkInterface,
    public deviceApi: DeviceApi,
    public ngzone:NgZone
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
  commonlist = [];

  device = null;
  online = false;

  account = null;

  onMyShow() {

    AppBase.TABName = "tab1";

    AppBase.LASTTAB = this;

    this.phoneapi.modellist({ orderby:'r_main.cutcount desc' }).then((modellist: any) => {
      this.modellist = modellist;
      console.log(this.modellist, '快快快');
    });

    this.memberApi.commonlist({ account_id: this.memberInfo.id,orderby:'model.cutcount desc' }).then((commonlist: any) => {
      this.commonlist = commonlist;
      console.log(this.commonlist, '哎哎哎');
    });

    this.checkingdevice = 0;
    this.devicelist = [];
    this.deviceinfo = null;
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {

      this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
        this.device = device;
      });

      this.sendTCP(account.device_deviceno, "SYNCSTATUS", "", (ret) => {
        var tcpret = ret.split("|");
        this.online = tcpret[0] == "OK";

        this.ngzone.run(() => { });
        setTimeout(() => {

          this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
            this.device = device;
            this.ngzone.run(() => { });
          });
        }, 1000);
      });
    });

  }


  check(checks) {
    console.log(checks);
    this.checks = checks;
  }
  todetails(id) {
    this.navigate("/cutdetails", { id: id })
  }

  async trycut() {
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {

      this.sendTCP(account.device_deviceno, "TRYCUT", "", (ret) => {
        var tcpret = ret.split("|");
        //alert(JSON.stringify(tcpret));
        if (tcpret[0] == "OK") {
          this.toast("试刻指令以下达");
        } else {
          this.showAlert("试刻失败，请查看机器是否正常联网");
        }
      });
    });
  }

}
