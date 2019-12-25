import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.page.html',
  styleUrls: ['./setting.page.scss'],
  providers: [MemberApi]
})
export class SettingPage extends AppBase {

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

  }
  sudu=0;
  gearratio=0;
  values=0;
  onMyLoad() {
    //参数
    this.params;
  }

  onMyShow() {

  }
  setdaoya(checking,daoya1,daoya2,daoya3,daoya4,daoya5) {
    this.navigate("/setdaoya", { id: checking,daoya1:daoya1,daoya2:daoya2,daoya3:daoya3,daoya4:daoya4,daoya5:daoya5 })
  }
  click(type) {
    console.log(type, '类型')
  }

  set(e) { 
    console.log(e.key,'略略略',this.sudu,'咳咳咳',this.gearratio)
  }
  changesudu(e,name){
    this.values=e.detail.value;

    this.memberApi.setmorendaoya({
      type:'Q',
      id: this.memberInfo.id,
      sudu:this.values
    }).then((ret) => {
      // console.log(ret)
    })
    console.log(name,'触发',e)
  }

  changexianwei(e){ 
    if(e.detail.checked==true){
     var xianwei='Y'
    }else{
      var xianwei='N'
    }
    this.memberApi.setmorendaoya({
      type:'N',
      id: this.memberInfo.id,
      xianwei:xianwei
    }).then((ret) => {
      console.log(ret)
    })
     
    console.log(e,'触发',e.detail.checked)
  }

}
