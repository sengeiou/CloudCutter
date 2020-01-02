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


        this.inteface2();


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
            var data2 = [];
            this.memberApi.cutlist({ type: 'B' }).then((cutlist: any) => {

                for (var a = 0; a < cutlist.length; a++) {
                    console.log(cutlist[a].cuttime, '空');
                    data2.push([cutlist[a].cuttime, cutlist[a].count]);
                }
                
                this.cutlist = cutlist;
                console.log(this.cutlist, '222222')
            })
        }
        //  this.onMyShow(); 

    }

    inteface(data) {
        var ec = echarts as any;
        var lineChart = ec.init(document.getElementById('lineChart'));

        var data = data;

        console.log(data, '领了', )

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



    inteface2() {
        var ec = echarts as any;
        var lineChart2 = ec.init(document.getElementById('lineChart2'));

        
        var option2 = {
            legend: {},
            tooltip: {
                trigger: 'axis',
                showContent: false
            },
            dataset: {
                source: [

                    [ '2012', '2013', '2014', '2015', '2016', '2017'],
                    [ 41.1, 30.4, 65.1, 53.3, 83.8, 98.7],
                    [ 86.5, 92.1, 85.7, 83.1, 73.4, 55.1],
                    [ 24.1, 67.2, 79.5, 86.4, 65.2, 82.5],
                    [ 55.2, 67.1, 69.2, 72.4, 53.9, 39.1]

                ]
            },
            xAxis: { type: 'category' },
            yAxis: { gridIndex: 0 },
            grid: { top: '10%' },
            series: [
                { type: 'line', smooth: true, seriesLayoutBy: 'row' },
                { type: 'line', smooth: true, seriesLayoutBy: 'row' },
                { type: 'line', smooth: true, seriesLayoutBy: 'row' },
                { type: 'line', smooth: true, seriesLayoutBy: 'row' },
            ]
        };



        lineChart2.setOption(option2);
    }

}
