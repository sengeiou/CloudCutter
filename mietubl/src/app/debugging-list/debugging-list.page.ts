import { Component, ViewChild, NgZone } from '@angular/core';
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
  selector: 'app-debugging-list',
  templateUrl: './debugging-list.page.html',
  styleUrls: ['./debugging-list.page.scss'],
  providers:[MemberApi,DeviceApi,PhoneApi]
})
export class DebuggingListPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi:MemberApi,
    public deviceApi:DeviceApi,
    public phoneApi:PhoneApi,
    public ngZone:NgZone
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480; 

  }

  onMyLoad(){
    //参数
    this.params;
  }
  list=[];
  onMyShow(){
    var socket=new TCPSocket("120.77.151.197","6123");
    socket.SendForText("APP,LIST",(ret)=>{
      var list=ret.split(",");
      var kv=[];
      for(let item of list){
        kv.push(item.split("|"));
      }
      this.list=kv;
      alert(JSON.stringify(this.list));
      this.ngZone.run(()=>{});
    });
  }

}
