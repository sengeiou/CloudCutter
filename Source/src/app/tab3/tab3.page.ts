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
    onMyShow() {
        AppBase.TABName = "tab3";
        AppBase.LASTTAB = this;
        this.inteface();
        this.inteface2();
    }
    check(checks) {
        console.log(checks);
        this.checks = checks;

      //  this.onMyShow();


    }

    inteface() {
        var ec = echarts as any;
        var lineChart = ec.init(document.getElementById('lineChart'));


        var base = +new Date(2019, 9, 3);
        var oneDay = 24 * 3600 * 1000;
        var valueBase = Math.random() * 300;
        var valueBase2 = Math.random() * 190;
        var data = [];
        var data2 = [];

        for (var i = 1; i < 10; i++) {
            var now = new Date(base += oneDay);
            var dayStr = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('-');

            valueBase = valueBase;
            valueBase <= 0 && (valueBase = Math.random() * 300);
            data.push([dayStr, valueBase]);
            // Math.round((Math.random() - 0.5) * 20 + valueBase2);
            valueBase2 = valueBase2;
            valueBase2 <= 0 && (valueBase2 = Math.random() * 50);
            data2.push([dayStr, valueBase2]);
        }

        var option = {
            animation: false,
            title: {
                left: 'center',
                // text: '触屏 tooltip 和 dataZoom 示例',
                //  subtext: '"tootip" and "dataZoom" on mobile device',
            },
            legend: {
                top: 'bottom',
                data: ['意向aa', '实践', '理论', '意识', '行为']
            },
            tooltip: {
                triggerOn: 'click',
                position: function (pt) {
                    return [pt[0], 130];
                }
            },
            // toolbox: {
            //     left: 'center',
            //     itemSize: 25,
            //     top: 55,
            //     feature: {
            //         dataZoom: {
            //             yAxisIndex: 'none'
            //         },
            //         restore: {}
            //     }
            // },
            xAxis: {
                type: 'time',
                // boundaryGap: [0, 0],
                axisPointer: {
                    value: '2019-10-7',//默认选中日期
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
                    name: '数据1',
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
                },
                {
                    name: '数据2',
                    type: 'line',
                    smooth: true,
                    stack: 'a',
                    symbol: 'circle',
                    symbolSize: 5,
                    sampling: 'average',
                    itemStyle: {
                        normal: {
                            color: '#1AD8E2'
                        }
                    },
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
                    data: data2
                }

            ]
        };



        lineChart.setOption(option);
    }

    inteface2() {
        var ec = echarts as any;
        var lineChart2 = ec.init(document.getElementById('lineChart2'));


        var base = +new Date(2019, 9, 3);
        var oneDay = 24 * 3600 * 1000;
        var valueBase = Math.random() * 300;
        var valueBase2 = Math.random() * 190;
        var data = [];
        var data2 = [];

        for (var i = 1; i < 10; i++) {
            var now = new Date(base += oneDay);
            var dayStr = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('-');

            valueBase = valueBase;
            valueBase <= 0 && (valueBase = Math.random() * 300);
            data.push([dayStr, valueBase]);
            // Math.round((Math.random() - 0.5) * 20 + valueBase2);
            valueBase2 = valueBase2;
            valueBase2 <= 0 && (valueBase2 = Math.random() * 50);
            data2.push([dayStr, valueBase2]);
        }

        var option = {
            animation: false,
            title: {
                left: 'center',
                // text: '触屏 tooltip 和 dataZoom 示例',
                //  subtext: '"tootip" and "dataZoom" on mobile device',
            },
            legend: {
                top: 'bottom',
                data: ['意向aa', '实践', '理论', '意识', '行为']
            },
            tooltip: {
                triggerOn: 'click',
                position: function (pt) {
                    return [pt[0], 130];
                }
            },
            // toolbox: {
            //     left: 'center',
            //     itemSize: 25,
            //     top: 55,
            //     feature: {
            //         dataZoom: {
            //             yAxisIndex: 'none'
            //         },
            //         restore: {}
            //     }
            // },
            xAxis: {
                type: 'time',
                // boundaryGap: [0, 0],
                axisPointer: {
                    value: '2019-10-7',//默认选中日期
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
                    name: '数据1',
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
                },
                {
                    name: '数据2',
                    type: 'line',
                    smooth: true,
                    stack: 'a',
                    symbol: 'circle',
                    symbolSize: 5,
                    sampling: 'average',
                    itemStyle: {
                        normal: {
                            color: '#1AD8E2'
                        }
                    },
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
                    data: data2
                }

            ]
        };



        lineChart2.setOption(option);
    }

}
