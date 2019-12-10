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
import { OnInit } from '@angular/core';
import { TabsPage } from './tabs/tabs.page';
import { FileTransfer, FileUploadOptions, FileTransferObject } from '@ionic-native/file-transfer/ngx';
import { AppPage } from 'e2e/src/app.po';
import { from } from 'rxjs';

declare let wx: any;

export class AppBase implements OnInit {
    public needlogin = true;

    public static TABName = "";
    public static LASTTAB = null;
    public static CurrentRoute: Router = null;
    public static CurrentNav: NavController = null;

    public static myapp: AppComponent = null;
    public static instapi: InstApi = null;
    public static memberapi: MemberApi = null;
    public static wechatApi: WechatApi = null;
    public static UNICODE = "tuiliao";

    public statusBarStyle = "X";//{DARK}
    public uploadpath: string = ApiConfig.getUploadPath();
    public util = AppUtil;
    public static Resources = null;
    public res = null;
    public static InstInfo = null;
    public static MemberInfo = null;
    public InstInfo = {  h5sharelogo: "", h5sharetitle: "", h5sharedesc: "", tel: "", h5appid: "", kf: "", openning: "", successtips: "", orderneedknow: "", name: "", logo: "", memberlogo: "", undershipping: 0, shippingfee: 0, about1: "", about2: "", about3: "", about4: "", about5: "" ,customerservicemobile: "",currency_name:"HKD",comrate:0};
    
    public static MYBABY = [];
    public mybaby = [];
    public options = null;
    public params: Params = null;

    public formdata=null;

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
    isLoginPage = true;
    memberInfo={id:0,endmenber_time:""};

    public operatorinfo={id:0,name:"",photo:"",loginname:""};

    user_id=''
    ismember='否'

    static STATICRAND = "";

    bfscrolltop; // 获取软键盘唤起前浏览器滚动部分的高度

    public static devicename = "";


    public constructor(
        public router: Router,
        public navCtrl: NavController,
        public modalCtrl: ModalController,
        public toastCtrl: ToastController,
        public alertCtrl: AlertController,
        public activeRoute: ActivatedRoute) {

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

        this.formdata={};

        var longlivetoken=window.localStorage.getItem("UserToken");
        var token=window.sessionStorage.getItem("UserToken");
        if(token==null&&longlivetoken!=null){
            window.sessionStorage.setItem("UserToken",longlivetoken);
        }

    }
    setStatusBar() {
        //  this.statusBar.styleLightContent();
    }
    ngOnInit() {
        this.bfscrolltop = document.body.scrollTop;
        ApiConfig.SetUnicode(AppBase.UNICODE);
        this.getResources();
        this.getLang();
        this.getInstInfo();
        this.onMyLoad();
        

    }

    CheckPermission() {

        
            let day = null
            let month=null
            let date = new Date();
            let year = date.getFullYear();
            month = date.getMonth()+1;
            day = date.getDate();
            let hh = date.getHours();
            let mm = date.getMinutes(); 
            month = month>10?month:('0'+month)
            day  =  day>10?day:('0'+day)
            let nowtime = year + "-" + month +"-"+ day+ " "+hh+":"+mm


        console.log(AppBase.IsLogin,'5555')

        if (this.isLoginPage==false) {
            var token = window.localStorage.getItem("UserToken");
            this.user_id = window.localStorage.getItem("user_id");
            var isregister = window.localStorage.getItem("isregister");
            console.log(token,'2222')

            if (token == null) {
                if(isregister!=null){
                    console.log('kkkkkk')
                    // this.router.navigate(["re"]);
                    // this.router.navigate(["login"]);
                    window.localStorage.removeItem("isregister");
                }else {
                    this.router.navigate(["login"]);
                    AppBase.IsLogin = false;
                }
                
            } else {
                ApiConfig.SetToken(token);

                AppBase.memberapi.info({id:this.user_id}).then((memberinfo) => {
                    AppBase.IsLogin = memberinfo == null ? false : true;
                    console.log(memberinfo,'memberinfo')
                    if(memberinfo == null){
                        this.router.navigate(['login'])
                    }else{
                        this.memberInfo = memberinfo;
                        this.ismember = memberinfo.ismember
                    }
                    // console.log(this.memberInfo,'oooo')
                })
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
                console.log(instinfo,'instinfo');
                
                console.log("aaabbbccc", AppBase.STATICRAND);
                
            });
        } else {
            this.InstInfo = AppBase.InstInfo;
            // this.setWechatShare();
        }
    }
    getMemberInfo() {

        AppBase.memberapi.info({id:this.user_id}).then((memberinfo) => {
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
        if (AppBase.Resources == null) {
            AppBase.instapi.resources({}, false).then((res) => {
                AppBase.Resources = res;
                this.res = res;
            });
        } else {
            this.res = AppBase.Resources;
        }
    }
    static Lang=null;
    lang=[];
    langcode="tc";
    getLang() {
        if (AppBase.Lang == null) {
            AppBase.instapi.langs({}, false).then((res) => {
                console.log(res,'langlang')
                AppBase.Lang = res;
                this.refreshLang();
            });
        }else{
            this.refreshLang();
        }

    }

    refreshLang(){
        if(AppBase.Lang!=null){

            var langcode=window.localStorage.getItem("langcode");
            if(langcode!=null){
                this.langcode=langcode;
            }
            this.lang=AppBase.Lang[this.langcode];
            console.log("refreshLang",this.lang);
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
        ref.complete();
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
            buttons: ["知道了"]
        });
        alert.present();
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

            this.confirm("确定登出账号么？", (ret) => {
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
    uploadImage(module,aa){

    }
    backtotop(){
        //var bid=
    }

    // yyyy/mm/dd hh:mm:ss
    getchangedate(date){
        return date.replace(/-/g,'/')
    }
    // yyyy/mm/dd
    getdate(date){
        date = date.slice(0,date.length-9)
        return date.replace(/-/g,'/')
    }

     // yyyy/mm/dd hh:mm
     getdatemm(date){
        date = date.slice(0,date.length-3)
        return date.replace(/-/g,'/')
    }

    // yyyy年mm月dd日 hh:mm
    getdatech(date){
        let date1 = date.slice(0,4)
        let date2 = date.slice(5,7)
        let date3 = date.slice(8,10)
        let date4 = date.slice(10,date.length-3)
        return date1+"年"+date2+"月"+date3+"日"+date4
        console.log(date1,date2,date3)
        // return date.replace(/-/g,'年')
    }

    // yy/mm/dd hh:mm 
    getchangedatetime(date){
        date = date.slice(2,date.length-3)
        return date.replace(/-/g,'/')
    }

    // mm/dd hh:mm
    getchangetime(date){
        date = date.slice(5,date.length-3)
        return date.replace(/-/g,'/')
    }

    getchangemonthtime(date){
        date = date.slice(5,date.length-3)
        return date
    }
    
    // dd-mm-yyyy
    getDate(date){
        let arr = date.split('-')
        let newArr = []
        for(let i=0;i<arr.length;i++){
            newArr[arr.length-i] = arr[i]
        }

        return newArr.join("-").replace("-",'')
    }

    async uploadFile(transfer: FileTransfer, filepath: string, module: string) {
        filepath=filepath.split("?")[0];
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



    
JTPYStr()
{
    return '皑蔼碍爱翱袄奥坝罢摆败颁办绊帮绑镑谤剥饱宝报鲍辈贝钡狈备惫绷笔毕毙闭边编贬变辩辫鳖瘪濒滨宾摈饼拨钵铂驳卜补参蚕残惭惨灿苍舱仓沧厕侧册测层诧搀掺蝉馋谗缠铲产阐颤场尝长偿肠厂畅钞车彻尘陈衬撑称惩诚骋痴迟驰耻齿炽冲虫宠畴踌筹绸丑橱厨锄雏础储触处传疮闯创锤纯绰辞词赐聪葱囱从丛凑窜错达带贷担单郸掸胆惮诞弹当挡党荡档捣岛祷导盗灯邓敌涤递缔点垫电淀钓调迭谍叠钉顶锭订东动栋冻斗犊独读赌镀锻断缎兑队对吨顿钝夺鹅额讹恶饿儿尔饵贰发罚阀珐矾钒烦范贩饭访纺飞废费纷坟奋愤粪丰枫锋风疯冯缝讽凤肤辐抚辅赋复负讣妇缚该钙盖干赶秆赣冈刚钢纲岗皋镐搁鸽阁铬个给龚宫巩贡钩沟构购够蛊顾剐关观馆惯贯广规硅归龟闺轨诡柜贵刽辊滚锅国过骇韩汉阂鹤贺横轰鸿红后壶护沪户哗华画划话怀坏欢环还缓换唤痪焕涣黄谎挥辉毁贿秽会烩汇讳诲绘荤浑伙获货祸击机积饥讥鸡绩缉极辑级挤几蓟剂济计记际继纪夹荚颊贾钾价驾歼监坚笺间艰缄茧检碱硷拣捡简俭减荐槛鉴践贱见键舰剑饯渐溅涧浆蒋桨奖讲酱胶浇骄娇搅铰矫侥脚饺缴绞轿较秸阶节茎惊经颈静镜径痉竞净纠厩旧驹举据锯惧剧鹃绢杰洁结诫届紧锦仅谨进晋烬尽劲荆觉决诀绝钧军骏开凯颗壳课垦恳抠库裤夸块侩宽矿旷况亏岿窥馈溃扩阔蜡腊莱来赖蓝栏拦篮阑兰澜谰揽览懒缆烂滥捞劳涝乐镭垒类泪篱离里鲤礼丽厉励砾历沥隶俩联莲连镰怜涟帘敛脸链恋炼练粮凉两辆谅疗辽镣猎临邻鳞凛赁龄铃凌灵岭领馏刘龙聋咙笼垄拢陇楼娄搂篓芦卢颅庐炉掳卤虏鲁赂禄录陆驴吕铝侣屡缕虑滤绿峦挛孪滦乱抡轮伦仑沦纶论萝罗逻锣箩骡骆络妈玛码蚂马骂吗买麦卖迈脉瞒馒蛮满谩猫锚铆贸么霉没镁门闷们锰梦谜弥觅绵缅庙灭悯闽鸣铭谬谋亩钠纳难挠脑恼闹馁腻撵捻酿鸟聂啮镊镍柠狞宁拧泞钮纽脓浓农疟诺欧鸥殴呕沤盘庞国爱赔喷鹏骗飘频贫苹凭评泼颇扑铺朴谱脐齐骑岂启气弃讫牵扦钎铅迁签谦钱钳潜浅谴堑枪呛墙蔷强抢锹桥乔侨翘窍窃钦亲轻氢倾顷请庆琼穷趋区躯驱龋颧权劝却鹊让饶扰绕热韧认纫荣绒软锐闰润洒萨鳃赛伞丧骚扫涩杀纱筛晒闪陕赡缮伤赏烧绍赊摄慑设绅审婶肾渗声绳胜圣师狮湿诗尸时蚀实识驶势释饰视试寿兽枢输书赎属术树竖数帅双谁税顺说硕烁丝饲耸怂颂讼诵擞苏诉肃虽绥岁孙损笋缩琐锁獭挞抬摊贪瘫滩坛谭谈叹汤烫涛绦腾誊锑题体屉条贴铁厅听烃铜统头图涂团颓蜕脱鸵驮驼椭洼袜弯湾顽万网韦违围为潍维苇伟伪纬谓卫温闻纹稳问瓮挝蜗涡窝呜钨乌诬无芜吴坞雾务误锡牺袭习铣戏细虾辖峡侠狭厦锨鲜纤咸贤衔闲显险现献县馅羡宪线厢镶乡详响项萧销晓啸蝎协挟携胁谐写泻谢锌衅兴汹锈绣虚嘘须许绪续轩悬选癣绚学勋询寻驯训讯逊压鸦鸭哑亚讶阉烟盐严颜阎艳厌砚彦谚验鸯杨扬疡阳痒养样瑶摇尧遥窑谣药爷页业叶医铱颐遗仪彝蚁艺亿忆义诣议谊译异绎荫阴银饮樱婴鹰应缨莹萤营荧蝇颖哟拥佣痈踊咏涌优忧邮铀犹游诱舆鱼渔娱与屿语吁御狱誉预驭鸳渊辕园员圆缘远愿约跃钥岳粤悦阅云郧匀陨运蕴酝晕韵杂灾载攒暂赞赃脏凿枣灶责择则泽贼赠扎札轧铡闸诈斋债毡盏斩辗崭栈战绽张涨帐账胀赵蛰辙锗这贞针侦诊镇阵挣睁狰帧郑证织职执纸挚掷帜质钟终种肿众诌轴皱昼骤猪诸诛烛瞩嘱贮铸筑驻专砖转赚桩庄装妆壮状锥赘坠缀谆浊兹资渍踪综总纵邹诅组钻致钟么为只凶准启板里雳余链泄';
}
FTPYStr()
{
    return '皚藹礙愛翺襖奧壩罷擺敗頒辦絆幫綁鎊謗剝飽寶報鮑輩貝鋇狽備憊繃筆畢斃閉邊編貶變辯辮鼈癟瀕濱賓擯餅撥缽鉑駁蔔補參蠶殘慚慘燦蒼艙倉滄廁側冊測層詫攙摻蟬饞讒纏鏟産闡顫場嘗長償腸廠暢鈔車徹塵陳襯撐稱懲誠騁癡遲馳恥齒熾沖蟲寵疇躊籌綢醜櫥廚鋤雛礎儲觸處傳瘡闖創錘純綽辭詞賜聰蔥囪從叢湊竄錯達帶貸擔單鄲撣膽憚誕彈當擋黨蕩檔搗島禱導盜燈鄧敵滌遞締點墊電澱釣調叠諜疊釘頂錠訂東動棟凍鬥犢獨讀賭鍍鍛斷緞兌隊對噸頓鈍奪鵝額訛惡餓兒爾餌貳發罰閥琺礬釩煩範販飯訪紡飛廢費紛墳奮憤糞豐楓鋒風瘋馮縫諷鳳膚輻撫輔賦複負訃婦縛該鈣蓋幹趕稈贛岡剛鋼綱崗臯鎬擱鴿閣鉻個給龔宮鞏貢鈎溝構購夠蠱顧剮關觀館慣貫廣規矽歸龜閨軌詭櫃貴劊輥滾鍋國過駭韓漢閡鶴賀橫轟鴻紅後壺護滬戶嘩華畫劃話懷壞歡環還緩換喚瘓煥渙黃謊揮輝毀賄穢會燴彙諱誨繪葷渾夥獲貨禍擊機積饑譏雞績緝極輯級擠幾薊劑濟計記際繼紀夾莢頰賈鉀價駕殲監堅箋間艱緘繭檢堿鹼揀撿簡儉減薦檻鑒踐賤見鍵艦劍餞漸濺澗漿蔣槳獎講醬膠澆驕嬌攪鉸矯僥腳餃繳絞轎較稭階節莖驚經頸靜鏡徑痙競淨糾廄舊駒舉據鋸懼劇鵑絹傑潔結誡屆緊錦僅謹進晉燼盡勁荊覺決訣絕鈞軍駿開凱顆殼課墾懇摳庫褲誇塊儈寬礦曠況虧巋窺饋潰擴闊蠟臘萊來賴藍欄攔籃闌蘭瀾讕攬覽懶纜爛濫撈勞澇樂鐳壘類淚籬離裏鯉禮麗厲勵礫曆瀝隸倆聯蓮連鐮憐漣簾斂臉鏈戀煉練糧涼兩輛諒療遼鐐獵臨鄰鱗凜賃齡鈴淩靈嶺領餾劉龍聾嚨籠壟攏隴樓婁摟簍蘆盧顱廬爐擄鹵虜魯賂祿錄陸驢呂鋁侶屢縷慮濾綠巒攣孿灤亂掄輪倫侖淪綸論蘿羅邏鑼籮騾駱絡媽瑪碼螞馬罵嗎買麥賣邁脈瞞饅蠻滿謾貓錨鉚貿麽黴沒鎂門悶們錳夢謎彌覓綿緬廟滅憫閩鳴銘謬謀畝鈉納難撓腦惱鬧餒膩攆撚釀鳥聶齧鑷鎳檸獰甯擰濘鈕紐膿濃農瘧諾歐鷗毆嘔漚盤龐國愛賠噴鵬騙飄頻貧蘋憑評潑頗撲鋪樸譜臍齊騎豈啓氣棄訖牽扡釺鉛遷簽謙錢鉗潛淺譴塹槍嗆牆薔強搶鍬橋喬僑翹竅竊欽親輕氫傾頃請慶瓊窮趨區軀驅齲顴權勸卻鵲讓饒擾繞熱韌認紉榮絨軟銳閏潤灑薩鰓賽傘喪騷掃澀殺紗篩曬閃陝贍繕傷賞燒紹賒攝懾設紳審嬸腎滲聲繩勝聖師獅濕詩屍時蝕實識駛勢釋飾視試壽獸樞輸書贖屬術樹豎數帥雙誰稅順說碩爍絲飼聳慫頌訟誦擻蘇訴肅雖綏歲孫損筍縮瑣鎖獺撻擡攤貪癱灘壇譚談歎湯燙濤縧騰謄銻題體屜條貼鐵廳聽烴銅統頭圖塗團頹蛻脫鴕馱駝橢窪襪彎灣頑萬網韋違圍爲濰維葦偉僞緯謂衛溫聞紋穩問甕撾蝸渦窩嗚鎢烏誣無蕪吳塢霧務誤錫犧襲習銑戲細蝦轄峽俠狹廈鍁鮮纖鹹賢銜閑顯險現獻縣餡羨憲線廂鑲鄉詳響項蕭銷曉嘯蠍協挾攜脅諧寫瀉謝鋅釁興洶鏽繡虛噓須許緒續軒懸選癬絢學勳詢尋馴訓訊遜壓鴉鴨啞亞訝閹煙鹽嚴顔閻豔厭硯彥諺驗鴦楊揚瘍陽癢養樣瑤搖堯遙窯謠藥爺頁業葉醫銥頤遺儀彜蟻藝億憶義詣議誼譯異繹蔭陰銀飲櫻嬰鷹應纓瑩螢營熒蠅穎喲擁傭癰踴詠湧優憂郵鈾猶遊誘輿魚漁娛與嶼語籲禦獄譽預馭鴛淵轅園員圓緣遠願約躍鑰嶽粵悅閱雲鄖勻隕運蘊醞暈韻雜災載攢暫贊贓髒鑿棗竈責擇則澤賊贈紮劄軋鍘閘詐齋債氈盞斬輾嶄棧戰綻張漲帳賬脹趙蟄轍鍺這貞針偵診鎮陣掙睜猙幀鄭證織職執紙摯擲幟質鍾終種腫衆謅軸皺晝驟豬諸誅燭矚囑貯鑄築駐專磚轉賺樁莊裝妝壯狀錐贅墜綴諄濁茲資漬蹤綜總縱鄒詛組鑽緻鐘麼為隻兇準啟闆裡靂餘鍊洩';
}
// 简->繁
Traditionalized(cc){
    var str='',ss=this.JTPYStr(),tt=this.FTPYStr();
    for(var i=0;i<cc.length;i++)
    {
        if(cc.charCodeAt(i)>10000&&ss.indexOf(cc.charAt(i))!=-1)str+=tt.charAt(ss.indexOf(cc.charAt(i)));
          else str+=cc.charAt(i);
    }
    return str;
}
// 繁->简
Simplized(cc){
    var str='',ss=this.JTPYStr(),tt=this.FTPYStr();
    for(var i=0;i<cc.length;i++)
    {
        if(cc.charCodeAt(i)>10000&&tt.indexOf(cc.charAt(i))!=-1)str+=ss.charAt(tt.indexOf(cc.charAt(i)));
          else str+=cc.charAt(i);
    }
    return str;
}



}