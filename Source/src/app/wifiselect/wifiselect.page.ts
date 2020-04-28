import { Component, ViewChild } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides, LoadingController } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { PhoneApi } from 'src/providers/phone.api';
import { AppComponent } from '../app.component';
declare var WifiWizard2: any;


@Component({
  selector: 'app-wifiselect',
  templateUrl: './wifiselect.page.html',
  styleUrls: ['./wifiselect.page.scss'],
})
export class WifiselectPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public activeRoute: ActivatedRoute,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi,
    public loadingCtrl:LoadingController
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.isLoginPage = true;
  }



  onMyLoad() {
    //参数
    this.params;
  }

  list = [];

  onMyShow() {
    // var cordova = null;
    // cordova = window.cordova.plugins;
    // let wifiManager = cordova.WifiManagerPlugin;
    // wifiManager.startWifiScan((ret) => {
    //   console.log("startWifiScan", ret);
    //   wifiManager.getAvailableNetworksList((list) => {
    //     console.log("getAvailableNetworksList", list);
    //     this.list = list;
    //   }, (err2) => {

    //     console.log("getAvailableNetworksList err", err2);
    //   }, true);
    // }, (err) => {
    //   console.log("startWifiScan err", err);
    //   this.nobackshowAlert(this.lang.wifipermission);
    // });

    

    WifiWizard2.timeout( 10000 ).then( function(){
				
    });
    this.loaddata();
  }
  count=0;
  async loaddata(){
    var loading2 = await this.loadingCtrl.create({  backdropDismiss: false });
    await loading2.present();

    var that=this;
    WifiWizard2.scan().then( function( results ){
      console.log( 'getting results!', results );
      loading2.dismiss();
      //alert(JSON.stringify(results));
      var rk=[];
      for(var i=0;i<results.length;i++){
        try{
          console.log("k",results[i]);
          if(results[i].SSID!=''){
            var isduplic=false;
            for(var j=0;j<rk.length;j++){
              if(results[i].SSID==rk[j]){
                isduplic=true;
                break;
              }
            }
            if(isduplic==false){
              rk.push(results[i].SSID);
            }
          }
        }catch(e){
          console.log("loop",e);
        }
      }
      that.list=rk;
      if(rk.length==0){
        that.showAlert("无法获取您的wifi热点列表，请尝试手动配置");
      }
		}).catch( function( error ){
      loading2.dismiss();
      that.showAlert("无法获取您的wifi热点列表，请尝试手动配置");
		});
  }

  returnconn(ssid) {
    window.sessionStorage.setItem("ssid", ssid);
    this.back();
  }

}

