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

  types = '';
  values = 0;
  onMyLoad() {
    //参数
    this.params;
    this.check = this.params.id;
    // this.value1 = this.params.daoya1;
    // this.value2 = this.params.daoya2;
    // this.value3 = this.params.daoya3;
    // this.value4 = this.params.daoya4;
    // this.value5 = this.params.daoya5;
  }

  onMyShow() {

  }
  checks(idx, num) {
    this.memberApi.setmorendaoya({
      id: this.memberInfo.id,
      daoya: num,
      checking: idx
    }).then((ret) => {
      console.log(ret)
      this.check = idx;
    })
  }

  changes(e, name) {
    //this.value1 = e.detail.value
    console.log(name, '触发2222222', e);
 
    this.memberApi.setmorendaoya({
      type: 'Y',
      id: this.memberInfo.id,
      daoya: e.detail.value,
      fenlei: name
    }).then((ret) => {
       if(this.check==name){
        
        this.memberApi.setmorendaoya({
          id: this.memberInfo.id,
          daoya: e.detail.value,
          checking: name
        }).then((ret) => {
          console.log(ret)
          this.check = name;
        })

       }
    })
 
  }

  setname(e, daoyaname) {
    console.log('---', e.detail.value, daoyaname, '---') 
       
      this.memberApi.setmorendaoya({
        type: 'K',
        id: this.memberInfo.id,
        daoya: e.detail.value,
        fenlei: daoyaname
      }).then((ret) => {
  
      })
  }
  
  // set(value, num) {
  //   console.log(value, '理论', num, '理论')

  //   this.memberApi.setmorendaoya({
  //     type: 'Y',
  //     id: this.memberInfo.id,
  //     daoya: value,
  //     fenlei: num
  //   }).then((ret) => {
  //      if(this.check==num){
        
  //       this.memberApi.setmorendaoya({
  //         id: this.memberInfo.id,
  //         daoya: value,
  //         checking: num
  //       }).then((ret) => {
  //         console.log(ret)
  //         this.check = num;
  //       })


  //      }
  //   })
  // }




  

}

