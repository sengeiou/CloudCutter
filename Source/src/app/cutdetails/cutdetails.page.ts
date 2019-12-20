import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { PhoneApi } from 'src/providers/phone.api';

@Component({
  selector: 'app-cutdetails',
  templateUrl: './cutdetails.page.html',
  styleUrls: ['./cutdetails.page.scss'],
  providers: [MemberApi, PhoneApi]
})


export class CutdetailsPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public phoneapi: PhoneApi,
    public memberApi: MemberApi
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;

  }
  modelinfo = '';
  daoyalist=[];
  checks='';
  onMyLoad() {
    //参数
    this.params;
    this.phoneapi.modelinfo({id:this.params.id}).then((modelinfo: any) => {
      this.modelinfo = modelinfo;
      console.log(this.modelinfo, '快快快')
    })
    this.phoneapi.daoyalist({}).then((daoyalist: any) => {
      this.daoyalist = daoyalist;
      console.log(this.daoyalist, '慢慢慢')
    })
  }

  onMyShow() {

  }
  check(checks){
    console.log(checks,'选择');
    this.checks=checks;
  }
  cut(){
    
    this.showConfirm('确认切割!', (ret)=>{
      if(ret==false){
        console.log('失败')
      }else{
        console.log('成功')
        this.DD();
      }
    });
 
  }
  DD(){
    this.memberApi.consumecount({
      account_id:this.memberInfo.id,
      model_id:this.params.id, 
     }).then((ret)=>{ 
       if(ret!=undefined){
        this.toast('切割成功!');
       }else{
         this.toast('切割失败!');
       }
          // console.log(ret,'充值失败'); 
     })
  }
  backagain(){
    this.navCtrl.navigateBack('tabs/tab2');
  }
}
