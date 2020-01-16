import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import {PhoneApi} from 'src/providers/phone.api';

@Component({
  selector: 'app-choosemodel',
  templateUrl: './choosemodel.page.html',
  styleUrls: ['./choosemodel.page.scss'],
  providers:[MemberApi,PhoneApi]
})
 
export class ChoosemodelPage  extends AppBase {

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

  onMyLoad(){

    //参数
    this.params;
    this.phoneapi.modellist({brand_id:this.params.id,cutclassify_id:this.params.classify_id,orderby:'r_main.seq',status:'A'}).then((modellist:any)=>{
      this.modellist= modellist;
      console.log(this.modellist,'快快快')
    })
  }

  onMyShow(){
 
  }


  todetails(id,modelname,typename){
    this.navigate("/cutdetails", { id: id ,modelname:modelname+typename});
  }

}
