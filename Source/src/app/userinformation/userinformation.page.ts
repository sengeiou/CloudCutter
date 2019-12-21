import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { PhoneApi } from 'src/providers/phone.api';

@Component({
  selector: 'app-userinformation',
  templateUrl: './userinformation.page.html',
  styleUrls: ['./userinformation.page.scss'],
  providers: [MemberApi]
})
export class UserinformationPage extends AppBase {

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
  name = '';
  mobile = "";
  email = '';
  address = "";
  id = '';
  types = '';
  onMyLoad() {
    //参数
    this.params;
    this.types = this.params.types;
  }

  onMyShow() {

  }

  update() {
    console.log(this.memberInfo.id)
    console.log(this.name, this.mobile, this.email, this.address);
    //return;
    this.memberApi.updates({
      id: this.memberInfo.id,
      name: this.name,
      mobile: this.mobile,
      email: this.email,
      address: this.address
    }).then((ret) => {

      if (this.types == 'A') {
        this.back()
      } else {
        this.backToUrl("/tabs/tab1");
      }

    })
  }

}

