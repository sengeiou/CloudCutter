import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { DeviceApi } from 'src/providers/device.api';
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { PhoneApi } from 'src/providers/phone.api';

@Component({
  selector: 'app-debugging',
  templateUrl: './debugging.page.html',
  styleUrls: ['./debugging.page.scss'],
  providers:[MemberApi,DeviceApi,PhoneApi]
})
export class DebuggingPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi:MemberApi,
    public deviceApi:DeviceApi,
    public phoneApi:PhoneApi
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480; 

  }

  onMyLoad(){
    //参数
    this.params;
  }

  deviceno="33ffd605504232363267254300000000";
  deviceinfo=null;

  getDeviceInfo(){

    this.deviceApi.info({deviceno:this.deviceno}).then((deviceinfo)=>{
      this.deviceinfo=deviceinfo;
    });
  }

  aa(e){
    console.log(e);
  }
  modellist=[];
  onMyShow(){
    this.getDeviceInfo();
    this.phoneApi.modellist({}).then((modellist)=>{
      this.modellist=modellist;
    });
  }

  send(COMM,PARAM){

    var socket=new TCPSocket("120.77.151.197","6123");
    socket.SendForText("APP,"+this.deviceno+","+COMM+","+PARAM,(ret)=>{
      alert(ret);
      setTimeout(()=>{
        this.getDeviceInfo();
      },2000);
    });
  }
  trycutter(){
    this.send("TRYCUT","")
  }
}
