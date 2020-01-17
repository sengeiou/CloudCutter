import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { ContentApi } from 'src/providers/content.api';

@Component({
  selector: 'app-agreement',
  templateUrl: './agreement.page.html',
  styleUrls: ['./agreement.page.scss'],
  providers:[ContentApi]
})
export class AgreementPage  extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi:MemberApi,
    public contentApi:ContentApi
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480; 
      this.xieyi={};
  }

  xieyi=null;  
  mycontent=null; 
  show=0;
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
    }else if(this.params.type=='wjmm'){
      this.show=1;
      this.contentApi.get({keycode:"user"}).then((ret)=>{
        console.log(ret,'快捷键');
        var content=this.decode(ret.content);
        this.mycontent=content;
        
      });
    }
    else{
      this.memberApi.xieyi({}).then((xieyi: any) => { 
        this.xieyi=xieyi;
        console.log(xieyi)
      })
    }
    
  }
}
 
