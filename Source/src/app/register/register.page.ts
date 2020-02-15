import { Component, ViewChild ,ElementRef} from '@angular/core';
import { AppBase } from '../AppBase';
import { Router } from '@angular/router';
import {  ActivatedRoute, Params } from '@angular/router';
import { NavController, ModalController, ToastController, AlertController, NavParams,IonSlides } from '@ionic/angular';
import { AppUtil } from '../app.util';
import { DomSanitizer } from '@angular/platform-browser';
import { MemberApi } from 'src/providers/member.api';
import { AliyunApi } from 'src/providers/aliyun.api';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
  providers:[MemberApi,AliyunApi]
})
export class RegisterPage extends AppBase {

  constructor(public router: Router,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public ele: ElementRef,
    public activeRoute: ActivatedRoute,
    public aliyunApi:AliyunApi,
    public sanitizer: DomSanitizer,
    public memberApi:MemberApi,
  
    ) {
    super(router, navCtrl, modalCtrl, toastCtrl, alertCtrl,activeRoute);
    this.headerscroptshow = 480;
      this.isLoginPage=true;
  }

  onMyLoad(){
    //参数
    this.params;
  }
  needlogin=false;
  istelzhuce = true;

  username = "";
  password = "";
  mobile="";
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

  memberlist=null
  areacodelist=null
  areacode=""
  onMyShow(){

      this.memberApi.memberlist({}).then((memberlist:any)=>{
        console.log(memberlist)
        this.memberlist = memberlist
      })

      this.memberApi.areacodelist({}).then((areacodelist)=>{
        console.log(areacodelist)
        this.areacodelist = areacodelist.sort(this.compare("seq"))
        this.areacode=areacodelist[0].areacode;
      })
    

  }

  compare(pro){
    return function(a,b){
      return a[pro]-b[pro]
    }
  }

  checkcode(arr,str){
    for(let item of arr){
      if(item.mycode==str){
        return true
      }
    }
    return false
  }



  xiayibu(){
    if(this.username!=""){
      // this.checkcanregs("name",this.username)
     console.log('dddd')
     if(this.password.length>=6){
        if(this.mobile!=""){
          
          
            var codemobiles = this.areacode + this.mobile
            var verifycode =this.yanzhenma;
            this.aliyunApi.verifycode({
                mobile: codemobiles,
                verifycode:this.yanzhenma,
                type: "register"
              }).then(ret => {
                console.log(ret,'ret')
              if (ret.code == 0) {
        
                this.checkcanregs("name",this.username)
                this.show = 2;
              } else {
                this.toast(this.lang["verifyincorrect"]);
              }
            });
          
        }
        if(this.email!=""){

          var verifycode =this.yanzhenma2;
            this.aliyunApi.emailverifycodes({
                email: this.email,
                verifycode:this.yanzhenma2,
                type: "register"
              }).then(ret => {
                console.log(ret,'ret')
              if (ret.code == 0) {
                // this.checkcanregs("email",this.email)
                this.checkcanregs("name",this.username)
                this.show = 2;
              } else {
                this.toast(this.lang["verifyincorrect"]);
              }
            });

        }
     }else {
       this.toast(this.lang["passwordshort"]);
     }
     
    }else{
      this.toast(this.lang["loginnameempty"])
    }

    

  }


  checkcanregs(type,value){
    let obj={}
    obj[type]=value
    this.memberApi.checkcanreg(obj).then((checkcanreg)=>{
      console.log(checkcanreg,'checkcanreg')
      console.log(123123);
      if(checkcanreg.code=='0'){

        this.memberApi.adduser({
          email:this.email,
          mobile: this.mobile,
          name: this.username,
          password: this.password,
          code: this.code,
          status: 'A'
        }).then(ret => {
          if (ret.code == "0"){
            this.toast(this.lang["registrysuccess"]);
            this.store("UserToken", ret.return);
            this.backToUrl("/login");
          } else {
            this.toast(ret.result);
          }
        });
        
      }else{
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
  reminder2=0;
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

  telzhuce(e){
    this.istelzhuce = true
    console.log(e)
    e.target.classList.add('zhuce-active')
    e.target.parentElement.childNodes[1].classList.remove('zhuce-active')


  }

  testmobile(mobile){
    console.log()
    console.log(this.areacode,'aaa')
    return true;
   }

  sendVerifyCode() {
    // alert(1);
    this.memberApi.checkcanreg({ mobile: this.mobile}).then(ret => {
      console.log(ret);

      if (ret.code == "0") {
        // this.inverify = true;
        console.log(5555)
       
        let codemobile = this.areacode + this.mobile
        console.log(codemobile,'llllll')
          if(this.testmobile(this.mobile)){

            this.aliyunApi.phoneverifycode({
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

          }else {
            this.toast(this.lang["mobilenouseincorrect"])
          }
      } else {
        this.toast(this.lang["mobileuse"]);
      }
    });
  }

  sendVerifyCode2(){
    this.memberApi.checkcanreg({ email: this.email }).then(ret => {
      console.log(ret);

      if (ret.code == "0") {
        // this.inverify = true;
        this.aliyunApi.emailverifycode({
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
      } else {
        this.toast(this.lang["emailuse"]);
      }
    });
  }
  
  aa=1;
  emailzhuce(e){
    console.log(e,'eee')
   
    // e.target.classList.add('zhuce-active');
    // e.target.parentElement.childNodes[0].classList.remove('zhuce-active');
    if(e==1){
      this.aa = 1;
      this.onMyShow();
      this.istelzhuce = true;
    }else if(e==2) {
      this.aa=2;
      this.istelzhuce = false;
    }
  }

}
