import { Component, ViewChild, NgZone } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-equipment',
  templateUrl: './equipment.page.html',
  styleUrls: ['./equipment.page.scss'],
})
export class EquipmentPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi,
    public ngzone: NgZone
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;

  }
  neiron = '';
  length = 0;
  equipmentlist = [];
  values = 0;
  onMyLoad() {
    //参数
    this.params;

    this.memberApi.equipmentlist({}).then((equipmentlist: any) => {
      this.equipmentlist = equipmentlist;
      this.length = equipmentlist.length;
      console.log(this.equipmentlist, 'sss')
    })
  }

  onMyShow() {
    this.memberApi.equipmentlist({}).then((equipmentlist: any) => {
      this.equipmentlist = equipmentlist;

      this.ngzone.run(() => { });
      console.log(this.equipmentlist, '快快快')
    })

  }



  setdevice(device_id) {
    this.memberApi.setshebei({
      account_id: this.memberInfo.id,
      device_id: device_id
    }).then((setshebei: any) => {

      this.memberInfo.defaultdevice = device_id.toString();
      this.onMyShow();

      console.log(setshebei, '快快快')
    })
  }

  delete(id) {

    this.showConfirm('确认删除吗？', (ret) => {

      if (ret == false) {
        console.log('失败')
      } else {
        this.memberApi.deletedevice({
          id: id
        }).then((deletedevice: any) => {
          this.onMyShow();
          console.log(deletedevice, '快快快')
        })
      }
    })


  }


}

