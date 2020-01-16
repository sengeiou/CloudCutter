import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-recharge',
  templateUrl: './recharge.page.html',
  styleUrls: ['./recharge.page.scss'], 
  providers:[MemberApi]
})
export class RechargePage  extends AppBase {

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
  rechargelist=[];
  check=0;
  onMyLoad(){
    //参数
    this.params; 
    this.memberApi.rechargelist({orderby:'r_main.seq',status:'A'}).then((rechargelist:any)=>{
      this.rechargelist= rechargelist;
      console.log(this.rechargelist,'快快快')
    })
  }
 
  onMyShow(){
 
  }
  choose(idx){
     this.check=idx;
  }
  zhifu(){
    
    //return;
    this.memberApi.addcutcount({
     account_id:this.memberInfo.id,
     account_count:this.rechargelist[this.check].count,
     account_price:this.rechargelist[this.check].price
    }).then((ret)=>{ 
      if(ret!=undefined){
        this.showAlert(this.lang.chongqianok);
      }else{
        this.showAlert(this.lang.chongqianno);
      }
         // console.log(ret,'充值失败'); 
    })
    
    console.log(this.check,this.rechargelist[this.check],)
  }
  toxieyi(){
    this.navigate("/agreement", { type:'money'});
  }
}