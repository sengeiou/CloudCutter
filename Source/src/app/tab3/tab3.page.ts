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

import * as echarts from 'echarts';

@Component({
    selector: 'app-tab3',
    templateUrl: 'tab3.page.html',
    styleUrls: ['tab3.page.scss']
})
export class Tab3Page extends AppBase {

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
        this.currentpage = "tab3";

        // AppBase.TABName = "tab3";
        // AppBase.LASTTAB = this;
    }
    checks = 'A';
    cutlist = [];
    cutlist2 = [];
    cutlist3=[];
    days = null;
    checkday = 7;
    onMyShow() {
        AppBase.TABName = "tab3";
        AppBase.LASTTAB = this;
        var data = [];
        this.memberApi.cutlist({ type: 'A' }).then((cutlist: any) => {

            for (var a = 0; a < cutlist.length; a++) {
                console.log(cutlist[a].cuttime, '空');
                data.push([cutlist[a].cuttime, cutlist[a].count]);
            }

            this.cutlist = cutlist;
            this.inteface(data);
            console.log(this.cutlist, '慢慢慢')
        })

        // this.memberApi.cutlist({ type: 'B' }).then((cutlist: any) => {
        //     this.cutlist2 = cutlist
        // })

        //this.inteface2();


    }
    check(checks) {
        console.log(checks);
        this.checks = checks;
        if (checks == 'A') {
            var data = [];
            console.log('每日')
            this.memberApi.cutlist({ type: 'A' }).then((cutlist: any) => {

                for (var a = 0; a < cutlist.length; a++) {
                    console.log(cutlist[a].cuttime, '空');
                    data.push([cutlist[a].cuttime, cutlist[a].count]);
                }
                this.cutlist = cutlist;
                this.inteface(data);
                console.log(this.cutlist, '111111')
            })
        } else if (checks == 'B') {

            this.memberApi.cutlist({ type: 'C' }).then((cutlist: any) => {
                this.cutlist2 = cutlist;
                console.log(this.cutlist2, '销量列表数据')
            })

            this.day(7);


        } else if (checks == 'C') {

            this.memberApi.cutlist({orderby:'r_main.cuttime desc'}).then((cutlist:any)=>{
                this.cutlist3= cutlist;
                this.cutmore=15;
                console.log(this.cutlist3,'慢慢慢')
            })
        }
        //  this.onMyShow(); 

    }

    inteface(data) {
        //return;
        var ec = echarts as any;
        var lineChart = ec.init(document.getElementById('lineChart'));

        var data = data;

        console.log(data, '领了')

        var option = {
            animation: false,
            title: {
                left: 'center',

            },

            tooltip: {
                triggerOn: 'click',
                position: function (pt) {
                    return [pt[0], 130];
                }
            },

            xAxis: {
                type: 'time',

                axisPointer: {
                    value: data[0][0],//默认选中日期
                    snap: true,
                    lineStyle: {
                        color: '#004E52',
                        opacity: 0.5,
                        width: 2
                    },
                    label: {
                        show: true,
                        formatter: function (params) {
                            return echarts.format.formatTime('yyyy-MM-dd', params.value);
                        },
                        backgroundColor: '#004E52'
                    },
                    handle: {
                        show: false,
                        color: '#004E52',
                        size: 30
                    }
                },
                splitLine: {
                    show: false
                },
                axisLabel: {
                    inside: false,
                    margin: 10,

                },
            },
            yAxis: {
                type: 'value',
                axisTick: {
                    inside: true
                },
                splitLine: {
                    show: true
                },
                axisLabel: {
                    inside: false,
                    margin: 10,
                    formatter: '{value}'
                },
                z: 10
            },
            grid: {
                top: 50,
                left: 60,
                right: 50,
                height: 200
            },
            dataZoom: [{
                type: 'inside',
                throttle: 50
            }],
            series: [
                {
                    name: this.lang.xl,
                    type: 'line',
                    smooth: true,
                    symbol: 'circle',
                    symbolSize: 5,
                    sampling: 'average',
                    itemStyle: {
                        normal: {
                            color: '#1AD8E2'
                        }
                    },
                    stack: 'a',
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 0.5,
                                [{
                                    offset: 0,
                                    color: '#1AD8E2'
                                }, {
                                    offset: 1,
                                    color: 'white'
                                }])
                        }
                    },
                    data: data
                }

            ]
        };



        lineChart.setOption(option);
    }


    inteface2(cutlist) {
        var ec = echarts as any;
        var lineChart2 = ec.init(document.getElementById('lineChart2'));

        var xdata = [];
        var ydata = [];
        var counts=0;
        
        for (var i = 0; i < cutlist.length; i++) { 
            if(i<9){
                xdata.push(cutlist[i].modelname);
                ydata.push({value:cutlist[i].count,name:cutlist[i].modelname});
            }else{
                counts += parseInt(cutlist[i].count) ; 
            } 
        }
 
        console.log(counts,'总数')
        if(counts!=0){
            xdata.push(this.lang.qita);
            ydata.push({value:counts,name:this.lang.qita});
        }
         
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            series: [
                {
                    type: 'pie',
                    radius: ['40%', '60%'],
                    avoidLabelOverlap: true,
                    label: {
                        position: 'outer',
                        alignTo: 'none',
                        bleedMargin: 5
                    },
                    data: ydata
                }
            ]
        };

        lineChart2.setOption(option);
    }

    day(type) {
        // this.days = type;

        this.checkday = type;

        var daylist = [];
        var now = new Date();
        var nowtimespan = now.getTime();

        for (var i = 0; i < type; i++) {
            var formatTime = AppUtil.FormatDate(AppUtil.FormatDateTime(new Date(nowtimespan - i * 24 * 3600 * 1000)));
            daylist.push(formatTime)
        }

        //return;

        // this.inteface2(daylist.reverse());

        daylist = daylist.reverse();

        // console.log(type, daylist, daylist[0], daylist[type - 1])

        this.memberApi.cutlist({ type: 'B', startime: daylist[0], endtime: daylist[type - 1] }).then((cutlist) => {

            this.cutlist2 = cutlist;
            this.inteface2(cutlist);

        })


    }

    cutmore=15;

  addcutmore(){
    this.cutmore+=15;
  }

}
