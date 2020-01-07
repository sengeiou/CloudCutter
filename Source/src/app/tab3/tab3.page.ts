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
    days = null;
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
        } else {

            this.memberApi.cutlist({ type: 'C' }).then((cutlist: any) => {
 
                this.cutlist2 = cutlist;  

                console.log(this.cutlist2,'销量列表数据')
            })

             this.day(3);

             
        }
        //  this.onMyShow(); 

    }

    inteface(data) {
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
                    name: '销量',
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


    inteface2(daylist, series,modellist) {
        var ec = echarts as any;
        var lineChart2 = ec.init(document.getElementById('lineChart2'));

        console.log(modellist,'辣椒炒辣椒');
        var ret=[daylist];
        ret=ret.concat(modellist);
        console.log("ret",ret);
        var option2 = {
            legend: {},
            tooltip: {
                trigger: 'axis',
                showContent: false
            },
            dataset: {
                source: ret
            },
            xAxis: { type: 'category' },
            yAxis: { gridIndex: 0 },
            grid: { top: '10%' },
            series: series
        };



        lineChart2.setOption(option2);
    }

    day(type) {
        // this.days = type;

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

          
             
            // return;
            
            var series = [];
            var modellist=[];

            // for (var a = 0; a < cutlist2.length; a++) { 

            //     console.log(cutlist2[a].dete,'出来吧')
            //    modellist.push(cutlist2[a].dete);
            //    series.push({ name:cutlist2[a].modelname, type: 'line', smooth: true, seriesLayoutBy: 'row' });
            // }
            
             for (let item of cutlist) {
                 var data=[];
                  series.push({ name: item.modelname, type: 'line', smooth: true, seriesLayoutBy: 'row' });
                  for (var i = 0; i < daylist.length; i++) {
                    //modellist.push(item.dete);
                    var val=0;
                    var detelist=item.dete;
                     
                    for(var a=0;a<detelist.length;a++){
                        if(detelist[a].cuttime==daylist[i]){
                            val=detelist[a].count
                        } 
                    }

                    data.push(val);
                    
                  }
                  modellist.push(data);
            }

            // console.log(cutlist)

            // console.log(series,'--等等等--',modellist)

             this.inteface2(daylist, series,modellist);
        })


    }

}
