import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-setdaoya',
  templateUrl: './setdaoya.page.html',
  styleUrls: ['./setdaoya.page.scss'],
  providers:[MemberApi]
})
export class SetdaoyaPage  extends AppBase {

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
  check=0;
  daoya1='';
  daoya2='';
  types='';
  onMyLoad(){
    //参数
    this.params;
    this.check=this.params.id
  }
 
  onMyShow(){
    
  }
  checks(idx,num){
 
    this.memberApi.setmorendaoya({
      id: this.memberInfo.id,
      daoya:num,
      checking:idx
    }).then((ret) => { 
      console.log(ret)
     this.check=idx;
    })
  }
  click(type){
      this.types=type;
      console.log(type,'选择')
  }
  set(e){
     
    if(this.types=="1"){
     var daoyao= this.daoya1;
    }
    if(this.types=="2"){
      var daoyao= this.daoya2;
    }

    this.memberApi.setmorendaoya({
      type:'Y',
      id: this.memberInfo.id,
      daoya:daoyao, 
      fenlei:this.types
    }).then((ret) => { 

    })
    
    console.log(e,e.key,'略略略',this.daoya1,this.daoya2)

  }
  
}
 
