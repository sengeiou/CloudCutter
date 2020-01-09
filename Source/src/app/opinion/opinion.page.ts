import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-opinion',
  templateUrl: './opinion.page.html',
  styleUrls: ['./opinion.page.scss'],
  providers:[MemberApi]
})
export class OpinionPage  extends AppBase {

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
  fankui='';
  fankuilist=[];
  //check='';
  onMyLoad(){
    //参数
    this.params;
  }
 
  onMyShow(){
    this.memberApi.fankuilist({account_id:this.memberInfo.id}).then((fankuilist: any) => { 
      for(var i=0;i<fankuilist.length;i++){
        fankuilist[i].check='A'; 
      } 
      this.fankuilist = fankuilist;
        console.log(this.fankuilist)
    })
  }
  submit(){
    this.showConfirm(this.lang.queren+'?', (ret)=>{
      if(ret==false){
        console.log('失败')
      }else{
        console.log('成功') 
        this.memberApi.fankui({
          account_id:this.memberInfo.id,
          content:this.fankui,
          status:'A'
        }).then(( ) => {
          this.toast(this.lang.tijiaook);
        })

        
      }
    });
   console.log(this.fankui,'理论')
  }

  show(idx){
    
    if(this.fankuilist[idx].check=='B'){
      this.fankuilist[idx].check='A';
    }else{
      this.fankuilist[idx].check='B';
    }

   
    
    
  }

}
 
