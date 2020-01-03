import { Component, ViewChild, NgZone } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides, LoadingController } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { PhoneApi } from 'src/providers/phone.api';
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { NetworkInterface } from '@ionic-native/network-interface/ngx';
import { Sender } from 'src/DataMgr/Sender';
import { DeviceApi } from 'src/providers/device.api';

@Component({
  selector: 'app-cutdetails',
  templateUrl: './cutdetails.page.html',
  styleUrls: ['./cutdetails.page.scss'],
  providers: [MemberApi, PhoneApi, DeviceApi]
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
    public deviceApi: DeviceApi,
    public loadingCtrl: LoadingController,
    public network: NetworkInterface,
    public ngzone: NgZone
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
  }

  statusnum = -1;
  kelustatus = ["检查设备在线状态", "检查设备刻录状态", "设置刀速", "设置刀压", "发送刻录文件", "完成"];
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
  }

  online = false;
  device = null;
  onMyShow() {

    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {

      this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
        this.device = device;
      });

      this.sendTCP(account.device_deviceno, "SYNCSTATUS", "", (ret) => {
        var tcpret = ret.split("|");
        this.online = tcpret[0] == "OK";

        setTimeout(() => {
          this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
            this.device = device;
          });
        }, 1000);
      });
    });
  }
  addcommon(model_id) {
    this.memberApi.addcommon({ account_id: this.memberInfo.id, model_id: model_id, status: 'A' }).then((addcommon) => {
      console.log(addcommon)
      this.toast('添加成功!');
    });
  }
  check(checks) {
    console.log(checks, '选择');
    this.checks = checks;
  }

  cut(cutclassify_id,daoya,sudu) { 
    this.showConfirm('确认切割!', (ret) => {
      if (ret == false) {
        console.log('失败')
      } else {
        console.log('成功')

        this.cutreal(daoya,sudu);
  
        this.memberApi.consumecount({
          account_id: this.memberInfo.id,
          model_id: this.params.id,
          cutclassify_id:cutclassify_id
        }).then((ret) => {
    
        })

      }
    });
  }

 
  backagain() {
    this.navCtrl.navigateBack('tabs/tab2');
  }

  closetips() {

    this.statusnum = -1;
  }

  async cutreal(daoya,sudu) {

    this.statusnum = 0;
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {

      this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
        this.device = device;
      });


           this.statusnum = 1;
           this.ngzone.run(() => { });
          setTimeout(() => {
            this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
              // this.device = device;
              console.log();
              if (device.machinestatus == 0) {
                this.statusnum = 2;
                this.ngzone.run(() => { });

                setTimeout(() => {
                  this.sendTCP(account.device_deviceno, "SPEED", sudu, (ret2) => {
                    var tcpret2 = ret2.split("|");
                    if (tcpret2[0] == "OK") {
                      this.statusnum = 3;
                      this.ngzone.run(() => { });
                      setTimeout(() => {

                        this.sendTCP(account.device_deviceno, "PRESSURE", daoya, (ret3) => {
                          var tcpret3 = ret3.split("|");
                          if (tcpret3[0] == "OK") {
                            this.statusnum = 4;
                            this.ngzone.run(() => { });
                            setTimeout(() => {
                              this.sendTCP(account.device_deviceno, "WRITE", this.params.id, (ret4) => {
                                var tcpret4 = ret4.split("|");
                                if (tcpret4[0] == "OK") {
                                  this.statusnum = 5;
                                  this.ngzone.run(() => { });
                                  this.toast("正在切膜");
                                } else {
                                  this.cuterror = "刻录出错：" + ret4;
                                  this.ngzone.run(() => { });
                                }
                              });
                            }, 2000);
                          } else {
                            this.cuterror = "设置刀压出错：" + ret3;
                            this.ngzone.run(() => { });
                          }
                        });
                      }, 2000);
                    } else {
                      this.cuterror = "设置刀速出错：" + ret2;
                      this.ngzone.run(() => { });
                    }
                  });
                }, 2000);
              } else {
                this.cuterror = device.machinestatus_name;
                this.ngzone.run(() => { });
              }
            });
          }, 2000);


    });


  }


  checkdevice(i, list) {
    if (i >= list.length) {
      this.cuterror = "找不到可用设备";
      return;
    }
  }
}
