import { Component, ViewChild, NgZone } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api'; 
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { ApiConfig } from '../api.config';

@Component({
  selector: 'app-equipment',
  templateUrl: './equipment.page.html',
  styleUrls: ['./equipment.page.scss'],
})
export class EquipmentPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi, 
    public ngzone: NgZone
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;

  }
  neiron = '';
  length = 0;
  equipmentlist = [];
  equipmentinfo='';
  account=null;
  values = 0;
  deviceno=0;
  devicelist=[];
  online = false;
  Interval=null;
   
  onMyLoad() {
    //参数
    this.params;
    this.deviceno=this.params.deviceno;

    this.list();

    this.Interval= setInterval(() => { 
      this.list();
    },  1000);
 
  }
  

  onMyShow() {

    this.memberApi.equipmentlist({}).then((equipmentlist: any) => {
       
      this.equipmentlist = equipmentlist; 
      this.ngzone.run(() => { }); 
    })

    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
     this.account=account;
    this.memberApi.equipmentinfo({id:account.defaultdevice}).then((equipmentinfo: any) => {

    console.log(AppBase.LastQrcode,'雷克萨觉得');

      if(equipmentinfo==null){
       return;
      } 
      
      var date= new Date(equipmentinfo.device_lastupdatetime);
      var lasttime= date.valueOf()/1000; 
      var nowtime=  new Date().valueOf()/1000 ;

      var cha=nowtime-lasttime;
      
      if(cha>60 && AppBase.tanchuan == ""){
        this.showConfirm(this.lang.zanweipeiwang, (ret) => { 
              if (ret == false) {
                console.log('取消') 
              } else {
                console.log('跳转',this.account.device_deviceno);
                var deviceno_2=(this.account.device_deviceno).slice(16, 24);
                this.navigate('config-device-ap',{deviceno_2:deviceno_2,deviceno_1:this.account.device_deviceno})
                 
              }
            })
      } 
      // console.log(equipmentinfo,'贾克斯'); 
    });

    })
    
  }

  list(){

    var ip=ApiConfig.remoteTCPServerIP();
    var pt=ApiConfig.remoteTCPServerPort();
    var socket=new TCPSocket(ip,pt);
    socket.SendForText("APP,LIST",(ret)=>{
        this.devicelist=ret;
    });
 
  }
   
  onMyUnload(){
   clearInterval(this.Interval); 
  }


  setdevice(device_id) {
    this.memberApi.setshebei({
      account_id: this.memberInfo.id,
      device_id: device_id
    }).then((setshebei: any) => {

      this.memberInfo.defaultdevice = device_id.toString();
      this.onMyShow();

      console.log(setshebei, '快快快')
    })
  }

  delete(id,device_id) {
   console.log(device_id);
   //return;
    this.showConfirm(this.lang.qrsc, (ret) => {

      if (ret == false) {
        console.log('失败')
      } else {
        this.memberApi.deletedevice({
          id: id,
          device_id:device_id
        }).then((deletedevice: any) => {
          this.onMyShow();
          console.log(deletedevice, '快快快')
        })
      }
    })


  }


}

