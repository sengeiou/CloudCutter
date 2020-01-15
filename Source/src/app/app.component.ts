import { Component } from '@angular/core';

import { Platform, ToastController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { InstApi } from 'src/providers/inst.api';
import { WechatApi } from 'src/providers/wechat.api';
import { MemberApi } from 'src/providers/member.api';
import { AppBase } from './AppBase';
import { Globalization } from '@ionic-native/globalization/ngx';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  providers: [InstApi, MemberApi, WechatApi,Globalization]
})
export class AppComponent {
  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    public toastCtrl: ToastController,
    public instApi: InstApi,
    public memberApi: MemberApi,
    public wechatApi: WechatApi,
    private globalization: Globalization
  ) {
    this.initializeApp();
    AppBase.instapi = this.instApi;
    AppBase.memberapi = this.memberApi;
    AppBase.wechatApi = this.wechatApi
  }
  static Instance: AppComponent = null;
  currentpage = "";
  backButtonPressedOnceToExit = false;

  initializeApp() {
    this.platform.ready().then(() => {
      
      this.globalization.getPreferredLanguage() .then((res:any) => {
        //this.yuyan=res+'這個';
         //console.log(res)
         //console.log('快樂快樂快樂')
         var lang=res.substr(0,2);

         if(lang=='ch'){
          AppBase.langcode='chn'
         }if(lang=='en'){
          AppBase.langcode='eng'
         }if(lang=='fr'){
          AppBase.langcode='fra'
         }if(lang=='es'){
          AppBase.langcode='esp'
         }if(lang=='po'){
          AppBase.langcode='por'
         }if(lang=='de'){
          AppBase.langcode='deu'
         }if(lang=='py'){
          AppBase.langcode='py'
         } 
        //  AppBase.langcode=res.substr(0,2);

      }) .catch(e => {
        //this.yuyan2=e+'那個';
         AppBase.langcode='eng'
      });

      this.statusBar.backgroundColorByHexString('#ffffff');
      this.statusBar.styleDefault();
      //this.statusBar.backgroundColorByHexString('#ffffff');
      this.splashScreen.hide();

      AppComponent.Instance = this;
      var _self = this;
      var platform: Platform = this.platform;
      document.addEventListener("backbutton", () => {

        if (this.currentpage == "tab1"
          || this.currentpage == "tab4"
          || this.currentpage == "tab5"
        ) {
          //当前为Tab状态, 判断是否为首页
          // if (app.getActiveNav().getActive().name != 'HomePage') { //能用, 但编辑器提示未定义
          if (_self.backButtonPressedOnceToExit == true) {
            navigator["app"].exitApp();
          }
          _self.backButtonPressedOnceToExit = true;
          _self.presentToast("再按一次就退出程序");
          setTimeout(function () {
            _self.backButtonPressedOnceToExit = false;
          }, 2000);

        }
        else {
          //app.goBack();
          if (AppBase.Current.isModal) {

            AppBase.Current.close();
          } else {

            AppBase.Current.back();
          }
        }
      });
 
    });
  }
  async presentToast(msg: string) {
    let toast = await this.toastCtrl.create({
      message: msg,
      duration: 2000
    });
    await toast.present();
  }
}
