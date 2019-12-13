import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import {PhoneApi} from 'src/providers/phone.api';
import { CutdetailsPage } from '../cutdetails/cutdetails.page';

@Component({
  selector: 'app-search',
  templateUrl: './search.page.html',
  styleUrls: ['./search.page.scss'],
  providers:[MemberApi,PhoneApi]
})
export class SearchPage  extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public phoneapi:PhoneApi,
    public memberApi:MemberApi
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480; 

  }
  modellist=[];
  length=0;
  neiron = '';
  onMyLoad(){
    //参数
    this.params;
    
  }
 
  onMyShow(){
 
  }

  search(e){
    console.log(e,'lkkk');

   // if (e.key == 'Enter') {
      console.log(this.neiron,'垃圾'); 

      this.phoneapi.modellist({searchkeyword:this.neiron}).then((modellist:any)=>{
        this.modellist= modellist;
        this.length=modellist.length;
        console.log(this.modellist,'快快快')
      })

   // }
  }
  todetails(id){
    console.log(id,'快乐就好');
   // return;
    this.navigate("/cutdetails", { id: id });
  }

}