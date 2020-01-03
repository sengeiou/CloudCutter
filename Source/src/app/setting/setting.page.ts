import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { DeviceApi } from 'src/providers/device.api';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.page.html',
  styleUrls: ['./setting.page.scss'],
  providers: [MemberApi, DeviceApi]
})
export class SettingPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi,
    public deviceApi: DeviceApi
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;

  }

  sudu = 0;
  gearratio = 0;
  values = 0;
  neiron = '';
  show = false;
  isok = false;
  onMyLoad() {
    //参数
    this.params;
    this.show == false;
    this.sudu = this.params.sudu;
  }

  device = null;
  online = false;

  onMyShow() {
    this.show == false;

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
  setdaoya(checking, daoya1, daoya2, daoya3, daoya4, daoya5) {
    this.navigate("/setdaoya", { id: checking, daoya1: daoya1, daoya2: daoya2, daoya3: daoya3, daoya4: daoya4, daoya5: daoya5 })
  }
  click(type) {
    console.log(type, '类型')
  }

  set(e) {
    console.log(e.key, '略略略', this.sudu, '咳咳咳', this.gearratio)
  }
  changesudu(e, name) {
    this.values = e.detail.value;

    this.memberApi.setmorendaoya({
      type: 'Q',
      id: this.memberInfo.id,
      sudu: this.values
    }).then((ret) => {
      // console.log(ret)
    })
    console.log(name, '触发', e)
  }

  changexianwei(e) {
    this.device.spacing = e.detail.checked == true ? 1 : 0;
    //alert(this.device.spacing);
    this.sendTCP(this.device.deviceno, "SPACING", this.device.spacing, (ret) => {
      // alert(ret);
      setTimeout(() => {
        this.deviceApi.info({ "deviceno": this.device.deviceno }).then((device) => {
          this.device = device;
        });
      }, 1000);
    });
  }

  clickxianwei() {
    if (this.isok == false) {
      this.show = true;
    } else {
      this.show = false;
    }
  }

  setchilun(x_axis, y_axis) {
      this.navigate("/setchilunbi", { x_axis: x_axis, y_axis: y_axis });
  }
  
  chongzhi() {
    //this.show = true;
    //this.showAlert("重置模式决定了没？我建议放到机器按钮，不要在app搞这个");
    //this.device.spacing = e.detail.checked == true ? 1 : 0;
    //alert(this.device.spacing);

    this.showConfirm("确定重置？您的机器的配置信息将丢失", () => {

      this.sendTCP(this.device.deviceno, "RESET", "2", (ret) => {
        // alert(ret);
        var tcpret = ret.split("|");
        if (tcpret[0] == "OK") {
          this.toast("重置成功");
        }
      });
    })

  }

  close() {
    this.show = false;
  }

  submit(account) {
    // console.log(this.neiron,account)
    //return;
    console.log(this.memberInfo)
    this.memberApi.login({
      account: account,
      password: this.neiron
    }).then((ret) => {
      if (ret.code == "0") {
        console.log(ret);
        this.show = false;
        this.isok = true;
        this.neiron = '';
      } else {
        this.toast("用户名或密码不正确");
        return;
      }
    })


  }

}
