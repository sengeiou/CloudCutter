import { Component, ViewChild, ElementRef } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides, IonInfiniteScroll, IonMenu } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { AppComponent } from '../app.component';
import { TabsPage } from '../tabs/tabs.page';
@Component({
  selector: 'app-tab4',
  templateUrl: './tab4.page.html',
  styleUrls: ['./tab4.page.scss'],
})
export class Tab4Page extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public memberApi: MemberApi,
    public activeRoute: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private elementRef: ElementRef
  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.currentpage = "tab4";
    
    // AppBase.TABName = "tab3";
    // AppBase.LASTTAB = this;
    //this.isLoginPage=true;

  }
  //types='A';
  neiron = '';
  show = false;
  isok = false;
  onMyShow(){
    AppBase.TABName = "tab4";
    AppBase.LASTTAB = this;


  }
  userinformation(){
    this.navigate("/userinformation", { types:'A' });
  }
  sets(){
    this.show = true;
 
  }


  submit(account,sudu,xianwei,checking,daoya) {

    // console.log(this.neiron,account)
    //return;

    console.log(this.memberInfo)
    this.memberApi.login({
      account: account,
      password: this.neiron
    }).then((ret) => {
      if (ret.code == "0") {
        console.log(ret);
        this.show = false;
        this.neiron = '';
        this.navigate("/setting", { sudu:sudu,xianwei:xianwei,checking:checking,daoya:daoya });
      } else {
        this.toast("用户名或密码不正确");
        return;
      }
    })
 
  }

  close() {
    this.show = false;
  }


}