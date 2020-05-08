import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  providers:[MemberApi]
})

export class LoginPage  extends AppBase {

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
  areacodelist = null;
  areacode = "";
  onMyLoad(){
    //参数

    this.params;
  }
 
  onMyShow(){
    this.memberApi.areacodelist({}).then((areacodelist) => {
      console.log(areacodelist)
      this.areacodelist = areacodelist.sort(this.compare("seq"))
      this.areacode = areacodelist[0].areacode;
    })
 
  }

  compare(pro) {
    return function (a, b) {
      return a[pro] - b[pro]
    }
  }

  forgetpassword(){
    // this.showConfirm(this.lang.settishi, (ret) => {
    //   if (ret == false) {
    //     console.log('失败')
    //   } else {
        
    //   }
    // })
    // console.log('忘记密码');
    this.navigate("forgetpwd");
  }
  login(){
    // console.log('试试水', this.areacode);
    // return
    this.memberApi.login({
      account:this.username,
      quhao:this.areacode,
      password:this.password
    }).then((ret) => {
      if (ret.code == "0") {
       console.log(ret);
       AppBase.IsLogin=true;
        this.store("lastloginname", this.username);
        this.store("UserToken", ret.return);
        this.store("user_id",ret.result);
        console.log(123123);
        this.backToUrl("/tabs/tab1");
        //this.backToUrl("/userinformation");
      //console.log(this.modelinfo, '快快快')
      }else {
        this.toast(this.lang.mimacuo);
      }
    })
  }
  register(){

     this.navigate("register")

  }
}
 