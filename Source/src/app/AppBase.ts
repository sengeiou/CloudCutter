import { ApiConfig } from "./api.config";
import { AppUtil } from "./app.util";
import { NavController, ModalController, ToastController, NavParams, AlertController }
    from "@ionic/angular";
import { InstApi } from "../providers/inst.api";
import { MemberApi } from "../providers/member.api";
import { WechatApi } from "../providers/wechat.api";
import { AppComponent } from "./app.component";
import { ReturnStatement } from "@angular/compiler";
import { ViewController } from '@ionic/core';
import { Router } from '@angular/router';
import { ActivatedRoute, Params } from '@angular/router';
import { OnInit, OnDestroy } from '@angular/core';
import { TabsPage } from './tabs/tabs.page';
import { FileTransfer, FileUploadOptions, FileTransferObject } from '@ionic-native/file-transfer/ngx';
import { AppPage } from 'e2e/src/app.po';
import { from } from 'rxjs';
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { Globalization } from '@ionic-native/globalization/ngx';
import { Language } from './lang';
declare let wx: any;

export class AppBase implements OnInit,OnDestroy {
    public needlogin = true;

    assets = "/assets/"

    public static TABName = "";
    public static LASTTAB = null;
    public static CurrentRoute: Router = null;
    public static CurrentNav: NavController = null;

    public static myapp: AppComponent = null;
    public static instapi: InstApi = null;
    
    public static memberapi: MemberApi = null;
    public static wechatApi: WechatApi = null;
  
    public static Globalization = null;

    public static UNICODE = "tuiliao";
  

    public statusBarStyle = "X";//{DARK}
    public uploadpath: string = ApiConfig.getUploadPath();
    public util = AppUtil;
    public static Resources = null;
    public res = null;
    public static InstInfo = null;
    public static MemberInfo = null;
    public InstInfo = { h5sharelogo: "", h5sharetitle: "", h5sharedesc: "", tel: "", h5appid: "", kf: "", openning: "", successtips: "", orderneedknow: "", name: "", logo: "", memberlogo: "", undershipping: 0, shippingfee: 0, about1: "", about2: "", about3: "", about4: "", about5: "", customerservicemobile: "", currency_name: "HKD", comrate: 0 };

    public static MYBABY = [];
    public mybaby = [];
    public options = null;
    public params: Params = null;

    public formdata = null;

    public keyt = "memberinfo99";
    public stat = "stat9";

    public heading = "推料";
    public font = "简体"

    public firseonshow = true;
    public scrolltop = 0;
    public headerscroptshow = 0;
    public static is_mongceng = false;
    public static IsLogin = false;

    static Current = null;
    currentpage = "";
    isLoginPage = false;
    memberInfo = { id: 0, endmenber_time: "",defaultdevice:0 };

    public operatorinfo = { id: 0, name: "", photo: "", loginname: "" };

    user_id = ''
    ismember = '否'

    static STATICRAND = "";

    bfscrolltop; // 获取软键盘唤起前浏览器滚动部分的高度

    public static devicename = "";


    public constructor(
        public router: Router,
        public navCtrl: NavController,
        
        public modalCtrl: ModalController,
        public toastCtrl: ToastController,
        public alertCtrl: AlertController,
        public activeRoute: ActivatedRoute

        ) {
            


        this.activeRoute.queryParams.subscribe((params: Params) => {
            console.log(params);
            this.params = params;
        });
        this.res = [];
        var stat = window.sessionStorage.getItem(this.stat);
        if (stat == null) {
            stat = parseInt((Math.random() * 99999.9).toString()).toString();
            window.sessionStorage.setItem(this.stat, stat);
        }
        AppBase.STATICRAND = stat;

        var memberinfo = window.localStorage.getItem(this.keyt);

        if (memberinfo != null) {
            AppBase.MemberInfo = JSON.parse(memberinfo);
        }
        console.log("rdw", AppBase.MemberInfo);

        this.formdata = {};

        var longlivetoken = window.localStorage.getItem("UserToken");
        var token = window.sessionStorage.getItem("UserToken");

        console.log(token, 'token')

        if (token == null && longlivetoken != null) {
            window.sessionStorage.setItem("UserToken", longlivetoken);
        }

    }
    setStatusBar() {
        //  this.statusBar.styleLightContent();
    }
    ngOnInit() {
        console.log('走没走')
        this.bfscrolltop = document.body.scrollTop;
        ApiConfig.SetUnicode(AppBase.UNICODE);
        //this.getResources();

        this.getLang();
        this.getInstInfo();
        this.onMyLoad();


    }

    CheckPermission() {


        let day = null
        let month = null
        let date = new Date();
        let year = date.getFullYear();
        month = date.getMonth() + 1;
        day = date.getDate();
        let hh = date.getHours();
        let mm = date.getMinutes();
        month = month > 10 ? month : ('0' + month)
        day = day > 10 ? day : ('0' + day)
        let nowtime = year + "-" + month + "-" + day + " " + hh + ":" + mm


        console.log(AppBase.IsLogin, '5555')


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
                // else {
                //     this.router.navigate(["login"]);
                //     AppBase.IsLogin = false;
                // }
                console.log('账户信息1')
            } else {
                ApiConfig.SetToken(token);

                AppBase.memberapi.accountinfo({ id: this.user_id }).then((accountinfo) => {
                    AppBase.IsLogin = accountinfo == null ? false : true;
                    console.log(accountinfo, 'memberinfo')
                    if (accountinfo == null) {
                        //this.router.navigate(['login'])
                    } else {
                        this.memberInfo = accountinfo;
                        this.ismember = accountinfo.ismember
                    }

                })
                console.log('账户信息')
            }
        }


    }


    onMyLoad() {
    }
    getInstInfo() {
        if (AppBase.InstInfo == null) {
            AppBase.instapi.info({}, false).then((instinfo) => {
                AppBase.InstInfo = instinfo;
                this.InstInfo = instinfo;
                console.log(instinfo, 'instinfo');

                console.log("aaabbbccc", AppBase.STATICRAND);

            });
        } else {
            this.InstInfo = AppBase.InstInfo;
            // this.setWechatShare();
        }
    }
    getMemberInfo() {

        AppBase.memberapi.info({ id: this.user_id }).then((memberinfo) => {
            if (memberinfo.id == 0 || memberinfo.mobile == undefined || memberinfo.mobile == "") {
                //alert("?");
                memberinfo = null;
            }
            this.memberInfo = memberinfo;

        });
    }

    shouye() {

        this.navigate("/tabs/tab1");

    }




    getResources() {
        console.log('看看走没走', AppBase.Resources)
        if (AppBase.Resources == null) {

            console.log('去去去');
            AppBase.instapi.resources({}, false).then((res) => {
                AppBase.Resources = res;
                this.res = res;
                console.log(this.res, '来来来');
            });

        } else {
            console.log(this.res, '来来来');
            this.res = AppBase.Resources;
        }
    }
    static Lang = null;
    
    lang: any = {};
    tab1=null;
    tab2=null;
    tab3=null;
    tab4=null;
    static langcode = "chn";
    getLang() {



        var langcode = window.localStorage.getItem("langcode");
        if (langcode != null) {
            AppBase.langcode = langcode;
        }
        this.lang = Language.defaultlang[AppBase.langcode];
        
        console.log("refreshLang", this.lang);



        if (AppBase.Lang == null) {
            AppBase.instapi.langs({}, false).then((res) => {
                console.log(res, 'langlang')
                AppBase.Lang = res;
                this.refreshLang();
            });
        } else {
            this.refreshLang();
        }

    }
     
    refreshLang() {
        if (AppBase.Lang != null) {

            var langcode = window.localStorage.getItem("langcode");
            if (langcode != null) {
                AppBase.langcode = langcode;   
            }
           

        //    AppBase.Globalization.getPreferredLanguage().then(res => {
        //     console.log('瞭解')
        //    });
             
            // AppBase.globalization.getPreferredLanguage() .then(res => {
            //     console.log(res)
            //     console.log('快樂快樂快樂')
            //  }) .catch(e => console.log(e,'第一'));

            this.lang = AppBase.Lang[AppBase.langcode]; 
            
            TabsPage.Instance.tab1 = AppBase.Lang[AppBase.langcode].home;
            TabsPage.Instance.tab2 = AppBase.Lang[AppBase.langcode].cp;
            TabsPage.Instance.tab3 = AppBase.Lang[AppBase.langcode].tj;
            TabsPage.Instance.tab4 = AppBase.Lang[AppBase.langcode].wode;
             
            console.log("refreshLang",  this.lang );
        }
    }

    consolelog(vi, value) {
        console.log({ vi, value });
    }
    ionViewDidEnter() {

        AppComponent.Instance.currentpage = this.currentpage;
        this.consolelog("123132", this.currentpage);
        console.log(this.currentpage);
        if (TabsPage.Instance != null) {
            TabsPage.Instance.currentpage = this.currentpage;
        }


        AppBase.CurrentRoute = this.router;
        AppBase.CurrentNav = this.navCtrl;
        AppBase.Current = this;

        this.CheckPermission();
        this.refreshLang();
        this.onMyShow();


    }

    onMyShow() {

    }


    onPullRefresh(ref) {
        this.onMyShow();
        setTimeout(() => { 
            ref.target.complete();
        }, 2000);
    }
    doRefresh(ref) {
        this.onPullRefresh(ref);
        // setTimeout(() => {
        //     ref.complete();
        // }, 1000);
    }
    onLoadMoreRefresh(ref) {
        ref.complete();
    }
    doInfinite(infiniteScroll) {
        this.onLoadMoreRefresh(infiniteScroll);
        // setTimeout(() => {
        //   infiniteScroll.complete();
        // }, 1000);
    }
    isbacking = false;
    back() {
        if (this.isbacking == true) {
            return;
        }
        this.isbacking = true;
        //alert(this.Params.fromtab);
        if (history.length < 2) {
            this.navCtrl.navigateBack('tabs/tab1');
            return;
        }
        if (this.params.fromtab != undefined) {
            this.navCtrl.navigateBack('tabs/' + this.params.fromtab);
        } else {
            this.navCtrl.back();
        }
    }
    backToUrl(url) {
        this.navCtrl.navigateBack(url);
    }
    close(data) {
        this.modalCtrl.dismiss(data);
    }
    returnData(data) {
        this.modalCtrl.dismiss(data);
    }
    windowslocation(url) {
        window.location.href = url;
    }
    navigate(pagename, param = {}, checkLogin = false) {
        if (checkLogin == true) {
            if (this.memberInfo.id == 0) {
                this.navigate("login");
                return;
            }
        }
        this.router.navigate([pagename], { queryParams: param });

    }
    async showModal(pageobj, param = {}, callback = null) {

        var modal = await this.modalCtrl.create({
            component: pageobj,
            componentProps: param
        });
        await modal.onDidDismiss().then((data) => {
            if (callback != null) {
                callback(data);
            }
        });
        await modal.present();
    }

    showContent(title, key) {
        this.navigate("content", { title, key });
        //this.showModal("ContentPage", { title, key });
    }

    decode(val) {
        return AppUtil.HtmlDecode(val);
    }
    contentToLine(str) {
        if (str == null) {
            return "";
        }
        return str.split("\n");
    }

    tel(tel) {
        window.location.href = "tel:" + tel;
    }
    async toast(msg) {
        if (msg == "") {
            return;
        }
        console.log(((msg.length / 3) + 1) * 1000);
        const toast = await this.toastCtrl.create({
            message: msg,
            duration: ((msg.length / 3) + 1) * 500
        });
        toast.present();
    }
    async showAlert(msg) {

        const alert = await this.alertCtrl.create({
            header: "提示",
            subHeader: msg,
            buttons: [{
                text: "知道了",
                handler: () => {
                    this.back();
                }
            }]
        });
        alert.present();
        console.log('滴滴')
    }
    async nobackshowAlert(msg) {

        const alert = await this.alertCtrl.create({
            header: "提示",
            subHeader: msg,
            buttons: [{
                text: "知道了",
                handler: () => {
                    
                }
            }]
        });
        alert.present();
        console.log('滴滴')
    }
    async showConfirm(msg, confirmcallback) {

        const alert = await this.alertCtrl.create({
            header: "提示",
            subHeader: msg,
            buttons: [{
                text: "取消",
                handler: () => {
                    console.log('Disagree clicked');

                    confirmcallback(false);
                }
            }, {
                text: "好的",
                handler: () => {
                    confirmcallback(true);
                }
            }]
        });
        alert.present();
    }
    async checkLogin(callback) {

    }

    async showActionSheet(actionSheetController, header, buttons) {
        const actionSheet = await actionSheetController.create({
            header: header,
            buttons: buttons
        });
        await actionSheet.present();
    }
    hasLogin() {
        return this.memberInfo != null;
    }
    logout() {

        // window.localStorage.removeItem("UserToken");
        // this.MemberInfo = null;

        this.confirm("确定退出账号么？", (ret) => {
            if (ret) {
                AppBase.IsLogin = false;
                window.localStorage.removeItem("UserToken");
                window.localStorage.removeItem("user_id");
                window.localStorage.removeItem("isregister");
                this.memberInfo = null;
                this.backToUrl('/login');
            }
        })

    }
    toLogin() {

        if (!AppBase.IsLogin) {
            this.router.navigate(["login"], { queryParams: {} });

        }

    }


    store(name, value = null) {
        if (value == null) {
            return window.localStorage.getItem(name);
        } else {
            window.localStorage.setItem(name, value);
            return "";
        }
    }

    async confirm(msg, confirmcallback) {

        const alert = await this.alertCtrl.create({
            header: "提示",
            subHeader: msg,
            buttons: [{
                text: "取消",
                handler: () => {
                    console.log('Disagree clicked');

                    confirmcallback(false);
                }
            }, {
                text: "确认",
                handler: () => {
                    confirmcallback(true);
                }
            }]
        });
        alert.present();
    }

    splitRow(content) {
        return content.split("\n");
    }

    getMemberPhoto(photo: string) {
        if (photo == null || photo == undefined || photo.trim() == "") {
            return this.uploadpath + "inst/" + this.InstInfo.logo;
        } else {
            return this.uploadpath + "member/" + photo;
        }
    }

    logScrollStart() {
        console.log("logScrollStart");
    }
    logScrolling(e) {
        console.log(e);
        this.scrolltop = e.detail.scrollTop;
    }
    logScrollEnd() {
        console.log("logScrollEnd");
    }
    gotoDiv(id) {
        var target = document.querySelector('#' + id);
        target.scrollIntoView();
    }

    tryLogin() {
        this.showModal("LoginPage", {});
    }


    backHome() {
        this.navCtrl.navigateBack('tabs/tab1');
        return;
    }
    uploadImage(module, aa) {

    }
    backtotop() {
        //var bid=
    }

    // yyyy/mm/dd hh:mm:ss
    getchangedate(date) {
        return date.replace(/-/g, '/')
    }
    // yyyy/mm/dd
    getdate(date) {
        date = date.slice(0, date.length - 9)
        return date.replace(/-/g, '/')
    }

    // yyyy/mm/dd hh:mm
    getdatemm(date) {
        date = date.slice(0, date.length - 3)
        return date.replace(/-/g, '/')
    }

    // yyyy年mm月dd日 hh:mm
    getdatech(date) {
        let date1 = date.slice(0, 4)
        let date2 = date.slice(5, 7)
        let date3 = date.slice(8, 10)
        let date4 = date.slice(10, date.length - 3)
        return date1 + "年" + date2 + "月" + date3 + "日" + date4
        console.log(date1, date2, date3)
        // return date.replace(/-/g,'年')
    }

    // yy/mm/dd hh:mm 
    getchangedatetime(date) {
        date = date.slice(2, date.length - 3)
        return date.replace(/-/g, '/')
    }

    // mm/dd hh:mm
    getchangetime(date) {
        date = date.slice(5, date.length - 3)
        return date.replace(/-/g, '/')
    }

    getchangemonthtime(date) {
        date = date.slice(5, date.length - 3)
        return date
    }

    // dd-mm-yyyy
    getDate(date) {
        let arr = date.split('-')
        let newArr = []
        for (let i = 0; i < arr.length; i++) {
            newArr[arr.length - i] = arr[i]
        }

        return newArr.join("-").replace("-", '')
    }

    async uploadFile(transfer: FileTransfer, filepath: string, module: string) {
        filepath = filepath.split("?")[0];
        let options: FileUploadOptions = {
            fileKey: 'img',
            fileName: filepath
        }
        //alert(filepath);

        var fileTransfer: FileTransferObject = await transfer.create();
        return fileTransfer.upload(filepath, ApiConfig.getFileUploadAPI() + "?field=img&module=" + module, options)
            .then((data) => {
                // success
                //alert(data.response);
                return data.response.toString().split("|~~|")[1];
            }, (err) => {
                this.showModal("上传失败，请联系管理员");
                // error
            })
    }

    sendTCP(deviceno, COMM, PARAM, callback) {
        var socket = new TCPSocket(ApiConfig.remoteTCPServerIP(), ApiConfig.remoteTCPServerPort());
        socket.SendForText("APP," + deviceno + "," + COMM + "," + PARAM, (ret) => {
            callback(ret);
        });
    }
  
    

    ngOnDestroy(): void {
        this.onMyUnload();
    }
    onMyUnload(){

    }

}