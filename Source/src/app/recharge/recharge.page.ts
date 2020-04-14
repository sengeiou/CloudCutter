import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { WechatApi } from 'src/providers/wechat.api';

declare let Wechat: any;

@Component({
  selector: 'app-recharge',
  templateUrl: './recharge.page.html',
  styleUrls: ['./recharge.page.scss'], 
  providers:[MemberApi,WechatApi]
})
export class RechargePage  extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi:MemberApi,
    public wechatApi:WechatApi
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480; 

  }
  rechargelist=[];
  check=0;
  account=[]
  onMyLoad(){
    //参数
    this.params; 
     
  }
 
  onMyShow(){

    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
      
      this.memberApi.rechargelist({distributor_id:account.distributor_id,orderby:'r_main.seq',status:'A'}).then((rechargelist:any)=>{
        this.rechargelist= rechargelist;
        console.log(this.rechargelist,'快快快')
      })
    });

 
  }
  choose(idx){
     this.check=idx;
  }
  zhifu(){
    var that=this;
    this.showConfirm(this.lang.confirmrecharge,(confirmret)=>{
      if(confirmret==true){
        this.wechatApi.prepay({
          account_id:this.memberInfo.id,
          account_subject:this.rechargelist[this.check].count.toString()+this.lang.cishu,
          recharge_id:this.rechargelist[this.check].id
         }).then((params)=>{ 
           //alert(JSON.stringify(params));
            Wechat.sendPaymentRequest(params, function (payreturn) {
              //alert(JSON.stringify(payreturn));
              that.toast(that.lang.paymentsuccess);
              that.back();
            }, function (reason) {
              that.nobackshowAlert(reason);
            });
         })
      }
    });
    //return;
    
    console.log(this.check,this.rechargelist[this.check],)
  }
  toxieyi(){
    this.navigate("/agreement", { type:'money'});
  }
}