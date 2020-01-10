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
  kelustatus = [this.lang.jianchazaixian,this.lang.jianchakelu,this.lang.setdaosu , this.lang.setdaoya, this.lang.fsklwj, this.lang.ok];
  cuterror = "";

  modelinfo = null;
  daoyalist = [];
  checks = '';
  account = null;
  modelname='';
  onMyLoad() {
    //参数
    this.params;
    this.modelname=this.params.modelname;
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
  addcommon(model_id) {
    this.memberApi.addcommon({ account_id: this.memberInfo.id, model_id: model_id, status: 'A' }).then((addcommon) => {
      console.log(addcommon)
      this.toast(this.lang.tianjiaok);
    });
  }
  check(checks) {
    console.log(checks, '选择');
    this.checks = checks;
  }

  cut(cutclassify_id, daoya, sudu, count,vip) {

    if (daoya == 0 || sudu == 0) {
      this.showConfirm(this.lang.settishi, (ret) => {

        if (ret == false) {
          console.log('失败')
        } else {
          console.log('成功')
          this.navigate('setting')
        }

      })

    } else {
      this.showConfirm(this.lang.querenqiege, (ret) => {
        if (ret == false) {
          console.log('失败')
        } else {
          console.log('成功')

          if (count <= 0&&vip!='Y') {
            this.toast(this.lang.csbzqcz) ;
            return;
          }

          console.log('aaa')
          this.cutreal(daoya, sudu);

          this.memberApi.consumecount({
            account_id: this.memberInfo.id,
            model_id: this.params.id,
            cutclassify_id: cutclassify_id,
            vip:vip
          }).then((ret) => {

          })

        }
      });
    }

  }


  backagain() {
    this.navCtrl.navigateBack('tabs/tab2');
  }

  closetips() {
    this.statusnum = -1;
    this.sendTCP(this.account.device_deviceno, "SYNCSTATUS", "", () => { });
  }

  async cutreal(daoya, sudu) {

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
                              this.toast(this.lang.cutting);
                            } else {
                              this.cuterror = this.lang.keluchucuo+"：" + ret4;
                              this.ngzone.run(() => { });
                            }
                          });
                        }, 500);
                      } else {
                        this.cuterror =this.lang.setdaoyacuo + ret3;
                        this.ngzone.run(() => { });
                      }
                    });
                  }, 500);
                } else {
                  this.cuterror = this.lang + ret2;
                  this.ngzone.run(() => { });
                }
              });
            }, 500);
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
      this.cuterror = this.lang.noshebei;
      return;
    }
  }

}
