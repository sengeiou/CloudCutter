import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-equipment',
  templateUrl: './equipment.page.html',
  styleUrls: ['./equipment.page.scss'],
})
export class EquipmentPage  extends AppBase {

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
  neiron = '';
  length=0;
  equipmentlist=[];
  onMyLoad(){
    //参数
    this.params;

    this.memberApi.equipmentlist({ }).then((equipmentlist:any)=>{
      this.equipmentlist= equipmentlist;
      this.length=equipmentlist.length;
      console.log(this.equipmentlist,'sss')
    })
  }
 
  onMyShow(){
    
 
  }

  search(e){
    console.log(e,'lkkk');

   // if (e.key == 'Enter') {
      console.log('垃圾',this.neiron,'垃圾'); 

      this.memberApi.equipmentlist({searchkeyword:this.neiron}).then((equipmentlist:any)=>{
        this.equipmentlist= equipmentlist;
        this.length=equipmentlist.length;
        console.log(this.equipmentlist,'快快快')
      })

   // }
  }
  setwifi(id){
    this.navigate("/setwifi", { id: id });
  }
}
 
