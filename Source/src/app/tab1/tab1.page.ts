import { Component, ViewChild, ElementRef, NgZone } from '@angular/core';
import { ApiConfig } from "../api.config";
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides, IonInfiniteScroll, IonMenu, LoadingController } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
//import { PhoneApi } from 'src/providers/phone.api';
import { PhoneApi } from 'src/providers/phone.api';
import { AppComponent } from '../app.component';
import { TabsPage } from '../tabs/tabs.page';
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { NetworkInterface } from '@ionic-native/network-interface/ngx';
import { Sender } from 'src/DataMgr/Sender';
import { isNgTemplate } from '@angular/compiler';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { DeviceApi } from 'src/providers/device.api';
import { Globalization } from '@ionic-native/globalization/ngx';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
  providers: [MemberApi, PhoneApi, DeviceApi, Globalization]
})
export class Tab1Page extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public loadingCtrl: LoadingController,
    public memberApi: MemberApi,
    public phoneapi: PhoneApi,
    public activeRoute: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private elementRef: ElementRef,
    public network: NetworkInterface,
    public deviceApi: DeviceApi,
    public ngzone: NgZone,
    private globalization: Globalization
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.currentpage = "tab1";

    // AppBase.TABName = "tab1";
    // AppBase.LASTTAB = this;

  }

  //检查设备，0未绑定，1检查中，2已获得，3无设备
  checkingdevice = 0;
  deviceinfo = null;
  devicelist = [];
  devices=[];
  checks = 'A';
  modellist = [];
  commonlist = [];

  show = false;

  device = null;
  online = false;

  account = null;
  yuyan = null;
  yuyan2 = null;

  onMyShow() {
    
    AppBase.TABName = "tab1";

    AppBase.LASTTAB = this;
    

    console.log(this.user_id,'看看这个user_id')
    
    this.checkingdevice = 0;
    this.devicelist = [];
    
    this.deviceinfo = null;

    this.phoneapi.modellist({ orderby: 'r_main.cutcount desc', limit: '10' }).then((modellist: any) => {
      this.modellist = modellist;
      console.log(this.modellist, '快快快');
    });

    
 
    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
      this.account=account;
        

      if(this.user_id!=null&&this.account.device_deviceno==''&&this.account.newaccount_value!='Y'){

        this.showConfirm(this.lang.wbsb, (ret) => {
  
          if (ret == false) {
            console.log('失败')
            this.memberApi.setstatus({ }).then((account) => {

            })
          } else {
            console.log('成功')
            this.memberApi.setstatus({ }).then((account) => {
              
              this.navigate('equipment')
            })
           
          }
  
        })
  
      }



      this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
      this.device = device;
 
      console.log(this.device,'信息')
      });

  
      this.sendTCP(account.device_deviceno, "SYNCSTATUS", "", (ret) => {
 
        var tcpret = ret.split("|");
        this.online = tcpret[0] == "OK";

        this.ngzone.run(() => { });
        setTimeout(() => {

          this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
            this.device = device;
            console.log( this.device,'信息2');
            this.ngzone.run(() => {});
          });
        }, 1000);

      });


    });

  }

  accountinfo() {


    if (this.isLoginPage != true) {

      var token = window.localStorage.getItem("UserToken");
      this.user_id = window.localStorage.getItem("user_id");
      var isregister = window.localStorage.getItem("isregister");
      console.log(token, '2222')
 
      if (token == null) {
        if (isregister != null) {
          console.log('kkkkkk')
          window.localStorage.removeItem("isregister");
        }
        else {
          this.router.navigate(["login"]);
          AppBase.IsLogin = false;
        }
        console.log('账户信息1')
      } else {
        ApiConfig.SetToken(token);
        AppBase.memberapi.accountinfo({ id: this.user_id }).then((accountinfo) => {
          AppBase.IsLogin = accountinfo == null ? false : true;
          console.log(accountinfo, 'memberinfo')
          if (accountinfo == null) {
            this.router.navigate(['login'])
          } else {
            this.memberInfo = accountinfo;
            this.ismember = accountinfo.ismember
          }

        })
        console.log('账户信息')
      }
    }

  }


  check(checks) {
    console.log(checks);
    this.checks = checks;
    if (checks == 'B') {
      this.memberApi.commonlist({ account_id: this.memberInfo.id, orderby: 'model.cutcount desc' }).then((commonlist: any) => {
        this.commonlist = commonlist;
        console.log(this.commonlist, '哎哎哎');
      });
    }
 
  }
  
  todetails(id, modelname, typename) {
    // console.log(id,modelname,typename)

    this.navigate("/cutdetails", { id: id, modelname: modelname + typename })
  }
  delete(id) {
    this.showConfirm(this.lang.qrsc, (ret) => {
      if (ret == false) {

      } else {
        this.memberApi.deletecommon({ id: id }).then((deletecommon) => {
          this.nobackshowAlert(this.lang.sccg);
          

          if (this.checks == 'B') {
            this.memberApi.commonlist({ account_id: this.memberInfo.id, orderby: 'model.cutcount desc' }).then((commonlist: any) => {
              this.commonlist = commonlist;
              console.log(this.commonlist, '哎哎哎');
            });
          }

          // this.onMyShow();
        });
      }

    })
  }

  async trycut() {

    this.show = true;

    setTimeout(() => {
      this.show = false; 
    }, 300)

    this.memberApi.accountinfo({ id: this.user_id }).then((account) => {
      this.sendTCP(account.device_deviceno, "SYNCSTATUS", "", (ret) => { 
          this.deviceApi.info({ "deviceno": account.device_deviceno }).then((device) => {
            if (device.machinestatus == 0) {
              this.realtrycut(account.device_deviceno);
            }else{
              this.showConfirm(this.lang.stillcut, (ret) => {
                if (ret) {
                  this.realtrycut(account.device_deviceno);
                }else{
                
                }
              })
            }
          });
       
      });

    });
  }

  realtrycut(deviceno){

   

    this.ngzone.run(()=>{});

    

    // alert(deviceno);

    this.sendTCP(deviceno, "TRYCUT", "", (ret) => {
      var tcpret = ret.split("|");
      //alert(JSON.stringify(tcpret));
      if (tcpret[0] == "OK") {
        this.toast(this.lang.zlxd);
      } else {
        this.nobackshowAlert(this.lang.sksb);
      }
    });
  }

}
