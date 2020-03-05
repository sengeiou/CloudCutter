import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-setexplain',
  templateUrl: './setexplain.page.html',
  styleUrls: ['./setexplain.page.scss'],
  providers:[MemberApi]
})
export class SetexplainPage  extends AppBase {

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
  code="";
  onMyLoad(){
    //参数
    this.params;
    if(this.params.code!=undefined){
      this.code=this.params.code;
    }
  }
 
  onMyShow(){
    
  }
  chang(e){
    this.code = e.detail.value;
  }

  baocun(account_id){
    this.memberApi.addshebei({
      account_id:account_id,
      code:this.code,
      status:'A'
    }).then((addshebei:any)=>{

       
      if(addshebei.code<0){
          this.nobackshowAlert(this.lang.wzdsb);
      }else if(addshebei.code==0){
          this.navCtrl.back();
      }else{
        this.nobackshowAlert(this.lang.ytjsb);
      }
       console.log(addshebei);
    })
  }
 
}