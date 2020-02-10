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
  areacode="852"
  onMyShow(){

      this.memberApi.memberlist({}).then((memberlist:any)=>{
        console.log(memberlist)
        this.memberlist = memberlist
      })

      this.memberApi.areacodelist({}).then((areacodelist)=>{
        console.log(areacodelist)
        this.areacodelist = areacodelist.sort(this.compare("seq"))
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
          
          if(this.code!=""){
            if(this.checkcode(this.memberlist,this.code)){
              var codemobiles = this.areacode + this.mobile
              var verifycode =this.yanzhenma;
              this.aliyunApi.verifycode({
                  mobile: codemobiles,
                  verifycode:this.yanzhenma,
                  type: "register"
                }).then(ret => {
                  console.log(ret,'ret')
                if (ret.code == 0) {
          
                  // this.checkcanregs("mobile",this.mobile)
                  this.checkcanregs("name",this.username)
                  this.show = 2;
                } else {
                  this.toast("验证码校验失败，请重新尝试");
                }
              });
  

            }else {
              this.toast("推广码输入错误！请重新输入")
              return
            }
          }else {
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
                this.toast("验证码校验失败，请重新尝试");
              }
            });
          }

          
        }
        if(this.email!=""){

          if(this.code!=""){
            if(this.checkcode(this.memberlist,this.code)){

              var verifycode =this.yanzhenma2;
              this.aliyunApi.emailverifycodes({
                  email: this.email,
                  verifycode:this.yanzhenma2,
                  type: "register"
                }).then(ret => {
                  console.log(ret,'ret')
                if (ret.code == 0) {
                  this.checkcanregs("name",this.username)
                  this.show = 2;
                } else {
                  this.toast("验证码校验失败，请重新尝试");
                }
              });

            }else {
              this.toast("推广码输入错误！请重新输入")
              return
            }
          }else {
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
                this.toast("验证码校验失败，请重新尝试");
              }
            });
          }

        }
     }else {
       this.toast("密码少于6位，请重新输入密码")
     }
     
    }else{
      this.toast("用户名为空，请重新填写！")
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
            this.toast("注册成功");
            this.store("UserToken", ret.return);
            this.backToUrl("/login");
          } else {
            this.toast(ret.result);
          }
        });
        
      }else{
        this.toast(checkcanreg.result);
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

  changcode(){
    console.log()
    console.log(this.areacode,'aaa')
    if(this.areacode=="852"){
     
      return  /^(5|6|8|9)\d{7}$/
     
    }else if(this.areacode == "86") {
      
     return  /^[1][3-8]\d{9}$/

    }else if(this.areacode == "853"){
     
      return /^[6]([8|6])\d{5}/

    }
   }

  sendVerifyCode() {
    // alert(1);
    this.memberApi.checkcanreg({ mobile: this.mobile}).then(ret => {
      console.log(ret);

      if (ret.code == "0") {
        // this.inverify = true;
        console.log(5555)
        var reg = this.changcode()
        console.log(reg,'reg')
        let codemobile = this.areacode + this.mobile
        console.log(codemobile,'llllll')
          if(reg.test(this.mobile)){

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

                this.toast("验证码已发送，请注意查收");
                this.diyici = true;
                this.setInVerify();
              } else {
                this.toast("验证码发送失败，请稍后重试");
              }
            });

          }else {
            this.toast('手机号码错误，请重新输入！')
          }
      } else {
        this.toast("手机号码已经被使用");
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

            this.toast("验证码已发送，请注意查收");
            this.diyici = true;
            this.setInVerify2();
          } else {
            this.toast("验证码发送失败，请稍后重试");
          }
        });
      } else {
        this.toast("邮箱已经被使用");
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
