import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-myaccount',
  templateUrl: './myaccount.page.html',
  styleUrls: ['./myaccount.page.scss'], 
  providers:[MemberApi]
})
export class MyaccountPage  extends AppBase {

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

  color: string = '#1890fb';
  backgound: string = '#eaeaea';
  radius: number = 100;
  current:number=0;
  max:number=0;

  checks='A';
  buyrecordlist=[];
  cutlist=[];
  onMyLoad(){
    //参数
    this.params;
  }
 
  onMyShow(){
    this.memberApi.buyrecordlist({}).then((buyrecordlist:any)=>{
      this.buyrecordlist= buyrecordlist;
      console.log(this.buyrecordlist,'快快快')
    })

    this.memberApi.cutlist({}).then((cutlist:any)=>{
      this.cutlist= cutlist;
      console.log(this.cutlist,'慢慢慢')
    })
  }
  check(checks){
    console.log(checks);
     this.checks=checks;
  }
 
 
}
