import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { type } from 'os';

@Component({
  selector: 'app-setchilunbi',
  templateUrl: './setchilunbi.page.html',
  styleUrls: ['./setchilunbi.page.scss'],
  providers:[MemberApi]
})
export class SetchilunbiPage  extends AppBase {

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

  }
  value1=0;
  value2=0;
  onMyLoad(){
    //参数
    this.params;
    this.value1=this.params.x_axis;
    this.value2=this.params.y_axis; 
  }
 
  onMyShow(){
 
  }
  changes(e,name){
    this.value1=e.detail.value
    console.log(name,'触发',e);
    this.set(this.value1,'P')
  }
  changes2(e,name){
    this.value2=e.detail.value
    console.log(name,'触发',e);
    this.set(this.value2,'R')
  }
  set(value,types){
   // return;  
    this.memberApi.setmorendaoya({
      type:types,
      id: this.memberInfo.id,
      axis:value
    }).then((ret) => {

    })
    
  }
}
 
