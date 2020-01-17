import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-agreement',
  templateUrl: './agreement.page.html',
  styleUrls: ['./agreement.page.scss'],
})
export class AgreementPage  extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi:MemberApi
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480; 
      this.xieyi={};
  }

  xieyi=null;  

  onMyLoad(){
    //参数
    this.params;
  }
 
  onMyShow(){
    if(this.params.type=='money'){
      this.memberApi.chongqianxieyi({}).then((chongqianxieyi: any) => { 
        this.xieyi=chongqianxieyi;
        console.log(this.xieyi)
      })
    }else{
      this.memberApi.xieyi({}).then((xieyi: any) => { 
        this.xieyi=xieyi;
        console.log(xieyi)
      })
    } 
    
  }
}
 
