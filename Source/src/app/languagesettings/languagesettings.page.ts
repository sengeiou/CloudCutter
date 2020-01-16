import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { ApiConfig } from '../api.config';

@Component({
  selector: 'app-languagesettings',
  templateUrl: './languagesettings.page.html',
  styleUrls: ['./languagesettings.page.scss'],
  providers:[MemberApi]
})
export class LanguagesettingsPage  extends AppBase {

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
  langcode=null;
  onMyLoad(){
    //参数
    this.params;
  }
 
  onMyShow(){
    this.langcode=AppBase.langcode;
  }
  change(val){
    window.localStorage.setItem("langcode",val);
     ApiConfig.SetTokenKey(val);
    this.langcode=val; 
    this.refreshLang();
    // this.navCtrl.back();
  }
}
 
