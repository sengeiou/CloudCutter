import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import {PhoneApi} from 'src/providers/phone.api';

@Component({
  selector: 'app-choosebrand',
  templateUrl: './choosebrand.page.html',
  styleUrls: ['./choosebrand.page.scss'],
  providers:[MemberApi,PhoneApi]
})
 
export class ChoosebrandPage  extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public phoneapi:PhoneApi,
    public memberApi:MemberApi
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480; 

  }
  brandlist=[];
  onMyLoad(){
    //参数
    this.params;
    this.phoneapi.brandlist({}).then((brandlist:any)=>{
      this.brandlist= brandlist;
      console.log(this.brandlist,'快快快')
    })
  }
 
  onMyShow(){
 
  }
}
