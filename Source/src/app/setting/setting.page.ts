import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.page.html',
  styleUrls: ['./setting.page.scss'],
  providers: [MemberApi]
})
export class SettingPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;

  }
 
  sudu=0;
  gearratio=0;
  values=0;
  xianwei=0;
  checking=0;
  daoya=0; 
  neiron='';
  show=false;
  isok=false;
  onMyLoad() {
    //参数
    this.params;
    this.show==false;
    this.sudu=this.params.sudu;
    this.xianwei=this.params.xianwei; 
    this.daoya=this.params.daoya;
    this.checking=this.params.checking;

  }

  onMyShow() {
   this.show==false;
  }
  setdaoya(checking,daoya1,daoya2,daoya3,daoya4,daoya5) {
    this.navigate("/setdaoya", { id: checking,daoya1:daoya1,daoya2:daoya2,daoya3:daoya3,daoya4:daoya4,daoya5:daoya5 })
  }
  click(type) {
    console.log(type, '类型')
  }

  set(e) { 
    console.log(e.key,'略略略',this.sudu,'咳咳咳',this.gearratio)
  }
  changesudu(e,name){
    this.sudu=e.detail.value;

    this.memberApi.setmorendaoya({
      type:'Q',
      id: this.memberInfo.id,
      sudu:this.sudu
    }).then((ret) => {
      // console.log(ret)
    })
    console.log(name,'触发',e)
  }

  changexianwei(e){
    console.log('触发啊啊啊啊')
    if(this.isok==true){
 
       this.isok=false;
      if(e.detail.checked==true){
        var xianwei='Y'
       }else{
         var xianwei='N'
       }
       this.memberApi.setmorendaoya({
         type:'N',
         id: this.memberInfo.id,
         xianwei:xianwei
       }).then((ret) => {
         console.log(ret)
       })
    }
    console.log(e,'触发',e.detail.checked)
  }

  clickxianwei(){
    if(this.isok==false){
      this.show=true;  
    } else{
      this.show=false; 
    }
  }

  setchilun(x_axis,y_axis,account){
    this.show=true; 
    if(this.isok==true){ 
      this.navigate("/setchilunbi", { x_axis: x_axis,y_axis:y_axis});
      this.isok=false;
      this.show=false;
    }
  }
  chongzhi(){
    this.show=true; 
  }

  close(){
   this.show=false;
  }

  submit(account){
   // console.log(this.neiron,account)
    //return;
    console.log(this.memberInfo)
    this.memberApi.login({
     account:account,
     password:this.neiron
    }).then((ret) => {
      if (ret.code == "0") {
       console.log(ret);
       this.show=false;
       this.isok=true; 
       this.neiron='';
      }else {
        this.toast("用户名或密码不正确");
        return;
      }
    })


  }

}
