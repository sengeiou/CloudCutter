import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-setdaoya',
  templateUrl: './setdaoya.page.html',
  styleUrls: ['./setdaoya.page.scss'],
  providers: [MemberApi]
})
export class SetdaoyaPage extends AppBase {

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
  check = 0;
  // daoya1='';
  // daoya2='';
  // daoya3='';
  // daoya4='';
  // daoya5='';

  // value1 = 0;
  // value2 = 0;
  // value3 = 0;
  // value4 = 0;
  // value5 = 0;
  daoya = 0;
  daoya1 = 0;
  daoya2 = 0;
  daoya3 = 0;
  daoya4 = 0;
  daoya5 = 0;
  daoyaname = "";
  daoyaname1 = "";
  daoyaname2 = "";
  daoyaname3 = "";
  daoyaname4 = "";
  daoyaname5 = "";

  types = '';
  values = 0;
  onMyLoad() {
    //参数
    this.params;
    this.check = this.params.id; 
  }
  account = null;
  onMyShow() {
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
      this.daoya1=account.daoya1;
      this.daoya2=account.daoya2;
      this.daoya3=account.daoya3;
      this.daoya4=account.daoya4;
      this.daoya5=account.daoya5;
      this.account = account;
    });
  }
  checks(idx, num) {
    this.memberApi.setmorendaoya({
      id: this.memberInfo.id,
      daoya: num,
      checking: idx
    }).then((ret) => {
      console.log(ret)
      this.check = idx;
      this.sendTCP(this.account.device_deviceno, "PRESSURE", num, (ret3) => { });
    })
  }
  update(name) {
    if (name == 1) { 
      if(this.daoya1>500){
        this.nobackshowAlert(this.lang.chaochu);
        this.daoya1=500;
      }
      this.daoya = this.daoya1;
    }
    if (name == 2) {
      if(this.daoya2>500){
        this.nobackshowAlert(this.lang.chaochu);
        this.daoya2=500;
      }
      this.daoya = this.daoya2; 
    }
    if (name == 3) {
      if(this.daoya3>500){
        this.nobackshowAlert(this.lang.chaochu);
        this.daoya3=500;
      }
      this.daoya = this.daoya3;
    }
    if (name == 4) {
      if(this.daoya4>500){
        this.nobackshowAlert(this.lang.chaochu);
        this.daoya4=500;
      }
      this.daoya = this.daoya4;
    }
    if (name == 5) {
      if(this.daoya5>500){
        this.nobackshowAlert(this.lang.chaochu);
        this.daoya5=500;
      }
      this.daoya = this.daoya5;
    }
 
    this.changes(name,this.daoya);

  }

  changes(name, daoya) {
   

    this.memberApi.setmorendaoya({
      type: 'Y',
      id: this.memberInfo.id,
      daoya: daoya,
      fenlei: name
    }).then((ret) => {
      console.log('默认改没改');
      if (this.check == name) {

        console.log('默认改没改');

        this.memberApi.setmorendaoya({
          id: this.memberInfo.id,
          daoya: daoya,
          checking: name
        }).then((ret) => {
           
          this.check = name;
          this.onMyShow();

          if (this.check == name) {
            this.sendTCP(this.account.device_deviceno, "PRESSURE", daoya, (ret3) => { });
            
          }
        })

      }
    })

  }

  updatename(name) {
    if (name == 1) {
      this.daoyaname = this.daoyaname1;
    }
    if (name == 2) {
      this.daoyaname = this.daoyaname2;
    }
    if (name == 3) {
      this.daoyaname = this.daoyaname3;
    }
    if (name == 4) {
      this.daoyaname = this.daoyaname4;
    }
    if (name == 5) {
      this.daoyaname = this.daoyaname5;
    }

    this.setname(name, this.daoyaname)

  }

  setname(name, daoyaname="") {
    

    this.memberApi.setmorendaoya({
      type: 'K',
      id: this.memberInfo.id,
      daoya: daoyaname,
      fenlei: name
    }).then((ret) => {

    })
  }

  

}

