import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-authlogin',
  templateUrl: './authlogin.page.html',
  styleUrls: ['./authlogin.page.scss'],
  providers:[MemberApi]
})
export class AuthloginPage  extends AppBase {

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
  username = '';
  password=""; 
  onMyLoad(){
    //参数
    this.params; 
  }
 
  onMyShow(){
 
  }

  authlogin(distributor_id){
    

    console.log('试试水', this.username);
    
    this.memberApi.updateloginqrcode({
      distributor_id:distributor_id,
      code:this.params.code,
      account_id:this.user_id
    }).then((ret) => {
      console.log(ret,'---',distributor_id);
      if(ret.code==0){
        this.back();
        console.log('撤退')
      }else{
        this.nobackshowAlert(this.lang.jxscw)
        console.log('没有撤退可言')
      }
      
    })
  }
}
 