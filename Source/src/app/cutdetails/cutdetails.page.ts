import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { PhoneApi } from 'src/providers/phone.api';
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { NetworkInterface } from '@ionic-native/network-interface/ngx';
import { Sender } from 'src/DataMgr/Sender';

@Component({
  selector: 'app-cutdetails',
  templateUrl: './cutdetails.page.html',
  styleUrls: ['./cutdetails.page.scss'],
  providers: [MemberApi, PhoneApi]
})


export class CutdetailsPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public phoneapi: PhoneApi,
    public memberApi: MemberApi,
    public network: NetworkInterface
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.modelinfo = {};
  }

  statusnum = -1;
  kelustatus = ["开始检测设备", "获取数据状态", "设置刀速", "设置刀压", "开始刻录", "刻录成功"];
  cuterror = "";

  modelinfo = null;
  daoyalist = [];
  checks = '';
  account = null;
  onMyLoad() {
    //参数
    this.params;
    this.phoneapi.modelinfo({ id: this.params.id }).then((modelinfo: any) => {
      this.modelinfo = modelinfo;
      console.log(this.modelinfo, '快快快')
    })
    this.phoneapi.daoyalist({}).then((daoyalist: any) => {
      this.daoyalist = daoyalist;
      console.log(this.daoyalist, '慢慢慢')
    })
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
      this.account = account;
    });
  }


  onMyShow() {
  }
  check(checks) {
    console.log(checks, '选择');
    this.checks = checks;
  }
  //

  // this.showConfirm('确认切割!', (ret)=>{
  //   if(ret==false){
  //     console.log('失败')
  //   }else{
  //     console.log('成功')
  //     this.DD();
  //   }
  // });

  //}
  DD() {
    this.memberApi.consumecount({
      account_id: this.memberInfo.id,
      model_id: this.params.id,
    }).then((ret) => {
      if (ret != undefined) {
        this.toast('切割成功!');
      } else {
        this.toast('切割失败!');
      }
      // console.log(ret,'充值失败'); 
    })
  }
  backagain() {
    this.navCtrl.navigateBack('tabs/tab2');
  }

  closetips() {

    this.statusnum = -1;
  }

  cut() {
    this.statusnum = 0;

    this.network.getWiFiIPAddress().then((wifiinfo) => {
      var ip = wifiinfo.ip;
      TCPSocket.GetSocketList(ip, 5000, (list) => {

        this.checkdevice(0, list);

      });
    });
  }


  checkdevice(i, list) {
    if (i >= list.length) {
      this.cuterror = "找不到可用设备";
    }
    var ipinfo = list[0];

    var deviceno = this.account.device_deviceno;
    var socket = new TCPSocket(ipinfo.ip, "5000");
    var sender = new Sender(socket);
    sender.readMachineStatus((machineres) => {

      if (deviceno != machineres.machineid) {
        this.checkdevice(i + 1, list);
        return;
      }
      this.statusnum = 1;
      if (machineres.machinestatus == 0) {
        this.statusnum = 2;
        sender.setSpeed(800, (setspeedret) => {
          this.statusnum = 3;
          sender.setBladePressure(800, (setpressret) => {

            this.statusnum = 4;
            sender.writeFile("text", this.modelinfo.filecontent, (ret) => {
              this.statusnum = 5;
            }, () => { });

          }, () => {
            this.cuterror = "找不到可用设备";
          })

        }, () => {
          this.cuterror = "找不到可用设备";
        })
      } else {
        this.cuterror = "当前设备状态不可用";
      }


    }, () => {
      this.checkdevice(i + 1, list);
    });
  }
}
