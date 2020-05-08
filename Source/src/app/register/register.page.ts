import { Component, ViewChild, ElementRef } from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams, IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { AliyunApi } from 'src/providers/aliyun.api';


@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
  providers: [MemberApi, AliyunApi]
})
export class RegisterPage extends AppBase {



  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public ele: ElementRef,
    public activeRoute: ActivatedRoute,
    public aliyunApi: AliyunApi,
    public sanitizer: DomSanitizer,
    public memberApi: MemberApi,

  ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl, activeRoute);
    this.headerscroptshow = 480;
    this.isLoginPage = true;
  }


  onMyLoad() {
    //参数
    this.params;
  }
  needlogin = false;
  istelzhuce = true;

  username = "";
  shopnumber = "";
  password = "";
  mobile = "";
  email = "";
  code = "";
  diyici = false;
  ismima = true;
  infocus = "";
  show = 1;
  timer = null;
  inverify = false;
  yanzhenma = "";
  yanzhenma2 = "";
  reminder = 0;


  c1 = "";
  c2 = "";
  c3 = "";
  c4 = "";
  shopinfo = null;
  shopname = "";
  shop_id = "";
  memberlist = null
  areacodelist = null
  areacode = ""
  onMyShow() {

    this.memberApi.areacodelist({}).then((areacodelist) => {
      console.log(areacodelist)
      this.areacodelist = areacodelist.sort(this.compare("seq"))
      this.areacode = areacodelist[0].areacode;
    })

    this.memberApi.shoplist({ shopnumber: '001' }).then((shopinfo: any) => {
      console.log(shopinfo, "pppp");
      // this.shoplist = shoplist.sort(this.compare("seq"))
      // this.shopname=shoplist[0].shopname;
      // this.shop_id=shoplist[0].id;  
    });


  }

  checked(i) {
    // var shoplist=this.shoplist;
    // console.log(shoplist[i].id,shoplist[i].name,'看炬华科技')
  }

  compare(pro) {
    return function (a, b) {
      return a[pro] - b[pro]
    }
  }

  checkcode(arr, str) {
    for (let item of arr) {
      if (item.mycode == str) {
        return true
      }
    }
    return false
  }

  selectshop() {
    this.memberApi.shoplist({ shopnumber: this.shopnumber }).then((shopinfo: any) => {
      if (shopinfo != null && shopinfo != undefined) {
        this.shopinfo = shopinfo;
      } else {
        this.toast(this.lang['bhcw']);
        this.shopinfo = "";
      }
    })
  }

  xiayibu() {
    console.log(this.shop_id, '这个', this.shopnumber);


    this.memberApi.shoplist({ shopnumber: this.shopnumber }).then((shopinfo: any) => {

      //  console.log(shopinfo,"pppp");

      if (shopinfo != null && shopinfo != undefined) {

        this.shopinfo = shopinfo;

        if (this.username != "") {

          if (this.password.length >= 6) {
            if (this.mobile != "") {


              var codemobiles = this.areacode + this.mobile
              var verifycode = this.yanzhenma;

              this.aliyunApi.verifycode({
                mobile: codemobiles, 
                verifycode: this.yanzhenma,
                type: "register"
              }).then(ret => {
                console.log(ret, 'ret')
                if (ret.code == 0) {

                  this.checkcanregs("name", this.username, this.shopinfo.id)
                  this.show = 2;
                } else {
                  this.toast(this.lang["verifyincorrect"]);
                }
              });

            }
            
            if (this.email != "") {

              var verifycode = this.yanzhenma2;
              this.aliyunApi.emailverifycodes({
                email: this.email,
                verifycode: this.yanzhenma2,
                type: "register"
              }).then(ret => {
                console.log(ret, 'ret')
                if (ret.code == 0) {
                  // this.checkcanregs("email",this.email)
                  this.checkcanregs("name", this.username, this.shopinfo.id)
                  this.show = 2;
                } else {
                  this.toast(this.lang["verifyincorrect"]);
                }
              });

            }
          } else {
            this.toast(this.lang["passwordshort"]);
          }

        } else {
          this.toast(this.lang["loginnameempty"])
        }

      } else {
        this.toast('经销商编号错误');
      }

      // this.shoplist = shoplist.sort(this.compare("seq"))
      // this.shopname=shoplist[0].shopname;
      // this.shop_id=shoplist[0].id;
    });

    return;

  }


  checkcanregs(type, value, shop_id) {
    let obj = {}
    obj[type] = value
    this.memberApi.checkcanreg(obj).then((checkcanreg) => {
      console.log(checkcanreg, 'checkcanreg')
      console.log(123123);
      if (checkcanreg.code == '0') {

        this.memberApi.adduser({
          email: this.email,
          mobile: this.mobile,
          name: this.username,
          quhao:this.areacode,
          distributor_id: shop_id,
          password: this.password,
          code: this.code,
          status: 'A'
        }).then(ret => {
          if (ret.code == "0") {
            this.toast(this.lang["registrysuccess"]);
            this.store("UserToken", ret.return);
            this.backToUrl("/login");
          } else {
            this.toast(ret.result);
          }
        });

      } else {
        this.toast(this.lang[checkcanreg.result]);
      }
    })
  }

 


  setInVerify() {


    var k = this.timer = setInterval(() => {
      if (this.reminder >= 0) {
        this.reminder--;
      }
      if (this.reminder < 0) {
        clearInterval(k);
      }

    }, 1000);
  }
  reminder2 = 0;

  setInVerify2() {


    var k = this.timer = setInterval(() => {
      if (this.reminder2 >= 0) {
        this.reminder2--;
      }
      if (this.reminder2 < 0) {
        clearInterval(k);
      }

    }, 1000);
  }

  telzhuce(e) {
    this.istelzhuce = true
    console.log(e)
    e.target.classList.add('zhuce-active')
    e.target.parentElement.childNodes[1].classList.remove('zhuce-active')


  }

  testmobile(mobile) {
    console.log()
    console.log(this.areacode, 'aaa')
    return true;
  }

  sendVerifyCode() {
    // alert(1);
    let codemobile = this.areacode + this.mobile
    console.log(codemobile)
    this.memberApi.checkcanreg({ mobile: this.mobile,quhao:this.areacode }).then(ret => {
      console.log(ret,'看看返回的');
      // return
      if (ret.code == "0") {
        // this.inverify = true;
        console.log(5555)

        console.log(codemobile, 'llllll')
        if (this.testmobile(this.mobile)) {

          this.aliyunApi.phoneverifycode({
            quhao:this.areacode,
            mobile: codemobile,
            type: "register"
          }).then(ret => {
            // alert(JSON.stringify(ret) )
            console.log(ret);
            if (ret.code == 0) {
              this.reminder = 60;
              this.show = 1;

              this.c1 = "";
              this.c2 = "";
              this.c3 = "";
              this.c4 = "";
              //this.$refs["inputc1"].focus();

              //var obj = this.ele.nativeElement.querySelector('#inputc1');
              //obj.focus();

              this.toast(this.lang["verifycodesent"]);
              this.diyici = true;
              this.setInVerify();
            } else {
              this.toast(this.lang["sendverifycodefail"]);
            }
          });

        } else {
          this.toast(this.lang["mobilenouseincorrect"])
        }
      } 
      else if(ret.code == "2"){
        this.toast(this.lang["enterphone"]);
      }
      else {
        this.toast(this.lang["mobileuse"]);
      }
    });
  }

  sendVerifyCode2() {
    this.memberApi.checkcanreg({ email: this.email }).then(ret => {
      console.log(ret);

      if (ret.code == "0") {
        // this.inverify = true;
        this.aliyunApi.emailverifycode({
          quhao:this.areacode,
          email: this.email,
          type: "register"
        }).then(ret => {
          console.log(ret);
          if (ret.code == 0) {
            this.reminder2 = 60;
            this.show = 1;

            this.c1 = "";
            this.c2 = "";
            this.c3 = "";
            this.c4 = "";
            //this.$refs["inputc1"].focus();

            //var obj = this.ele.nativeElement.querySelector('#inputc1');
            //obj.focus();

            this.toast(this.lang["verifycodesent"]);
            this.diyici = true;
            this.setInVerify2();
          } else {
            this.toast(this.lang["sendverifycodefail"]);
          }
        });
      } 
      else if(ret.code == "2"){
        this.toast(this.lang["enteremail"]);
      }
      else {
        this.toast(this.lang["emailuse"]);
      }
    });
  }

  aa = 1;
  emailzhuce(e) {
    console.log(e, 'eee')

    // e.target.classList.add('zhuce-active');
    // e.target.parentElement.childNodes[0].classList.remove('zhuce-active');
    if (e == 1) {
      this.aa = 1;
      this.onMyShow();
      this.istelzhuce = true;
    } else if (e == 2) {
      this.aa = 2;
      this.istelzhuce = false;
    }
  }



}
