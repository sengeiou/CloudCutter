import { IConnector } from './IConnector';

export class Sender {


    public constructor(public connector: IConnector) {
    }


    static READSPEED = [0xAA, 0x10];
    static READBLADEPRESS = [0xAA, 0x11];
    static READGEARRATE = [0xAA, 0x12];
    static READMACHINEID = [0xAA, 0x13];
    static READAPINFO = [0xAA, 0x14];
    static READSTAINFO = [0xAA, 0x15];
    static READXIANWEI = [0xBB, 0x18];
    static READFUKUAN = [0xBB, 0x19];

    static SETSPEED = [0xBB, 0x10];
    static SETBLADEPRESS = [0xBB, 0x11];
    static SETGEARRATE = [0xBB, 0x12];
    static SETMACHINEID = [0xBB, 0x13];
    static SETAPINFO = [0xBB, 0x14];
    static SETSTAINFO = [0xBB, 0x15];
    static RESET = [0xBB, 0x16];
    static TRYCUT = [0xBB, 0x17];
    static SETXIANWEI = [0xBB, 0x18];
    static SETFUKUAN = [0xBB, 0x19];


    static READMACHINESTATUS = [0xAA, 0x20];
    static WRITEFILE = [0xCC, 0x30];

    //1、读取速度
    readSpeed(callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.READSPEED[0]);
        data.push(0x00);
        data.push(Sender.READSPEED[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8],
                speed: this.getNumber2(ret[9], ret[10])
            };
            callback(result);
            this.close();
        }, errcallback);
    }
    //2、设置速度
    setSpeed(speed, callback, errcallback) {

        var speedbyt = this.convertNumber(speed, 4);
        var data: any[] = [];
        data.push(Sender.SETSPEED[0]);
        data.push(0x00);
        data.push(Sender.SETSPEED[1]);
        data.push(0x02);
        data.push(0x00);
        data.push(speedbyt[0]);
        data.push(speedbyt[1]);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }

    //3、读取刀压
    readBladePressure(callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.READBLADEPRESS[0]);
        data.push(0x00);
        data.push(Sender.READBLADEPRESS[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8],
                pressure: this.getNumber2(ret[9], ret[10])
            };
            callback(result);
            this.close();
        }, errcallback);
    }


    //4、设置刀压
    setBladePressure(pressure, callback, errcallback) {

        var spressurebyt = this.convertNumber(pressure, 4);
        var data: any[] = [];
        data.push(Sender.SETBLADEPRESS[0]);
        data.push(0x00);
        data.push(Sender.SETBLADEPRESS[1]);
        data.push(0x02);
        data.push(0x00);
        data.push(spressurebyt[0]);
        data.push(spressurebyt[1]);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }



    //5、读取齿轮比
    readGearRate(callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.READGEARRATE[0]);
        data.push(0x00);
        data.push(Sender.READGEARRATE[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8],
                xrate: this.getNumber2(ret[9], ret[10]),
                yrate: this.getNumber2(ret[11], ret[12])
            };
            callback(result);
            this.close();
        }, errcallback);
    }


    //6、设置齿轮比
    setGearRate(xrate, yrate, callback, errcallback) {

        var xratebyt = this.convertNumber(xrate, 4);
        var yratebyt = this.convertNumber(yrate, 4);
        var data: any[] = [];
        data.push(Sender.SETGEARRATE[0]);
        data.push(0x00);
        data.push(Sender.SETGEARRATE[1]);
        data.push(0x04);
        data.push(0x00);
        data.push(xratebyt[0]);
        data.push(xratebyt[1]);
        data.push(yratebyt[0]);
        data.push(yratebyt[1]);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }




    //7、读取机器码
    readMachineID(callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.READMACHINEID[0]);
        data.push(0x00);
        data.push(Sender.READMACHINEID[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8],
                machineid: this.getString(ret.slice(9, 17)),
            };
            callback(result);
            this.close();
        }, errcallback);
    }


    //8、设置机器码
    setMachineID(machineid, callback, errcallback) {

        var machineidbyte = this.convertString(machineid, 8);
        var data: any[] = [];
        data.push(Sender.SETMACHINEID[0]);
        data.push(0x00);
        data.push(Sender.SETMACHINEID[1]);
        data.push(0x08);
        data.push(0x00);
        data = data.concat(machineidbyte);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }


    //9、读AP模式下WIFI信息
    readAPInfo(callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.READAPINFO[0]);
        data.push(0x00);
        data.push(Sender.READAPINFO[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8],
                wifiname: this.getString(ret.slice(9, 25)),
                wifipassword: this.getString(ret.slice(25, 41)),
                ip: this.getIpAddress(ret.slice(41, 45)),
                subnet: this.getIpAddress(ret.slice(45, 49)),
                port: this.getNumber(ret[49], ret[50], ret[51], ret[52]),
            };
            callback(result);
            this.close();
        }, errcallback);
    }
    //10、读AP模式下WIFI信息
    setAPInfo(wifiname, wifipassword, ip, subnet, port, callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.SETAPINFO[0]);
        data.push(0x00);
        data.push(Sender.SETAPINFO[1]);
        data.push(0x08);
        data.push(0x00);
        data = data.concat(this.convertString(wifiname, 16));
        data = data.concat(this.convertString(wifipassword, 16));
        data = data.concat(this.convertIpAddress(ip));
        data = data.concat(this.convertIpAddress(subnet));
        data = data.concat(this.convertNumber(port, 8));
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }
    //11、读STA模式下wifi信息
    readSTAInfo(callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.READSTAINFO[0]);
        data.push(0x00);
        data.push(Sender.READSTAINFO[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {
            var wificount = ret[9];
            var wifilist = [];
            for (var i = 0; i < wificount; i++) {
                var startcount = 10 + i * 16;
                var endcount = startcount + 16;
                var wifiname = this.getString(ret.slice(startcount, endcount));
                wifilist.push(wifiname);
            }


            var result = {
                machinestatus: ret[7],
                resultcode: ret[8],
                wificount: wificount,
                wifilist: wifilist
            };
            callback(result);
            this.close();
        }, errcallback);
    }

    //12、设置并进入STA模式下wifi模式下
    setSTAInfo(wifiname, wifipassword, callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.SETSTAINFO[0]);
        data.push(0x00);
        data.push(Sender.SETSTAINFO[1]);
        data.push(0x20);
        data.push(0x00);
        data = data.concat(this.convertString(wifiname, 16));
        data = data.concat(this.convertString(wifipassword, 16));
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }



    //11、读STA模式下wifi信息
    readXIANWEI(callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.READXIANWEI[0]);
        data.push(0x00);
        data.push(Sender.READXIANWEI[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {


            var result = {
                machinestatus: ret[7],
                resultcode: ret[8],
                "限位是否使能": ret[9],
            };
            callback(result);
            this.close();
        }, errcallback);
    }

    //12、设置并进入STA模式下wifi模式下
    setXIANWEI(xianwei, callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.SETSTAINFO[0]);
        data.push(0x00);
        data.push(Sender.SETSTAINFO[1]);
        data.push(0x01);
        data.push(0x00);
        data.push(xianwei);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }



    //13、回复出厂值,mode：1 除wifi外其它参数恢复出厂值，2、wifi参数恢复出厂值
    resetMachine(mode, callback, errcallback) {

        var data: any[] = [];
        data.push(Sender.RESET[0]);
        data.push(0x00);
        data.push(Sender.RESET[1]);
        data.push(0x01);
        data.push(0x00);
        data.push(mode);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }

    //14、试刻
    tryCuy(callback, errcallback) {

        var data: any[] = [];
        data.push(Sender.TRYCUT[0]);
        data.push(0x00);
        data.push(Sender.TRYCUT[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }



    //15、读取机器状态
    readMachineStatus(callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.READMACHINESTATUS[0]);
        data.push(0x00);
        data.push(Sender.READMACHINESTATUS[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }


    //1、读取速度
    readFUKUAN(callback, errcallback) {
        var data: any[] = [];
        data.push(Sender.READFUKUAN[0]);
        data.push(0x00);
        data.push(Sender.READFUKUAN[1]);
        data.push(0x00);
        data.push(0x00);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8],
                fukuan: this.getNumber2(ret[9], ret[10])
            };
            callback(result);
            this.close();
        }, errcallback);
    }
    //2、设置速度
    setFUKUAN(fukuan, callback, errcallback) {

        var fukuanbyt = this.convertNumber(fukuan, 4);
        var data: any[] = [];
        data.push(Sender.SETFUKUAN[0]);
        data.push(0x00);
        data.push(Sender.SETFUKUAN[1]);
        data.push(0x02);
        data.push(0x00);
        data.push(fukuanbyt[0]);
        data.push(fukuanbyt[1]);
        this.send(data, (ret) => {
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            callback(result);
            this.close();
        }, errcallback);
    }

    //16、写壳绘文件
    writeFile(filename, filecontent, callback, errcallback) {
        filecontent=filecontent.trim();
        var filecontentlengthbyte = this.convertNumber(filecontent.length, 8);
        var filenamebyte = this.convertString(filename, 16);

        var data: any[] = [];
        data.push(Sender.WRITEFILE[0]);
        data.push(0x01);
        data.push(Sender.WRITEFILE[1]);
        data.push(0x14);
        data.push(0x00);
        data = data.concat(filecontentlengthbyte);
        data = data.concat(filenamebyte);


        var filecontentbyte = this.convertString(filecontent, filecontent.length);

        this.send(data, (ret) => {
            alert(JSON.stringify(ret));
            var result = {
                machinestatus: ret[7],
                resultcode: ret[8]
            };
            if (1 == 1 || result.machinestatus == 0x00) {
                var ci = 0x02;
                this.sendfile(ci,filecontentbyte);
            } else {
                callback(result);
            }
        }, errcallback);
    }
    sendfile(ci,filecontentbyte){
       
            if (filecontentbyte.length <= 1024) {
                ci = 0x00;
            }

            var filechunlk = filecontentbyte.slice(0, 1024);
            filecontentbyte = filecontentbyte.slice(1024);

            var filechunlkbyt = this.convertNumber(filechunlk.length, 4);
            var filedata = [];
            filedata.push(Sender.WRITEFILE[0]);
            filedata.push(ci);
            filedata.push(Sender.WRITEFILE[1]);
            filedata.push(filechunlkbyt[0]);
            filedata.push(filechunlkbyt[1]);
            filedata = filedata.concat(filechunlk);
            this.sendnoend(filedata,(ret)=>{
                if(ci!=0x00){
                    ci++;
                    this.sendfile(ci,filecontentbyte);
                }
            },()=>{});
    }

    convertNumber(num, len) {
        var intnum=parseInt(num);
        //alert(intnum);
        var a = intnum.toString(16);
        //alert(a);
        while (a.length < len) {
            a = "0" + a.toString();
        }
        //alert(a);
        var ret = [];
        for (var i = 0; i < len; i = i + 2) {
            ret.push(parseInt("0x" + a[i] + a[i + 1]));
        }
        ret.reverse();
        //alert(JSON.stringify(ret));
        return ret;
    }

    

    convertString(wifiname: string, num: number): any {
        var ret = [];
        for (var i = 0; i < num; i++) {
            if (wifiname.length > i) {
                ret.push(wifiname[i].charCodeAt(0));
            } else {
                ret.push(0x00);
            }
        }
        return ret;
    }
    convertIpAddress(ip: string): any {
        var ret = [];
        var ipexp = ip.split(".");
        for (let st of ipexp) {
            ret.push(parseInt(st));
        }
        return ret;

    }
    getNumber2(d1: any, d2: any) {
        return parseInt("0x" + d2.toString(16) + d1.toString(16));
    }
    getNumber(d1: any, d2: any, d3: any, d4: any) {
        return parseInt("0x" + d4.toString(16) + d3.toString(16) + d2.toString(16) + d1.toString(16));
    }
    getIpAddress(data: any) {
        var ret = [];
        for (let d of data) {
            ret.push(d);
        }
        return ret.join(".");
    }

    getString(data: []) {
       // alert(JSON.stringify(data));
        var ret = "";
        for (let d of data) {
            if (d != 0x00) {
                ret += String.fromCharCode(d);
            }
        }
        return ret;
    }




    send(command: any[], callback = undefined, errcallback = undefined) {
        var data: any[] = [];
        data.push(0x5A);
        data.push(0xA5);

        var d = 0x00;
        for (let item of command) {
            data.push(item);
            d += item;
        }
        d = d & 0xff;
        data.push(d);
        data.push(0x0D);
        data.push(0x0A);
        console.log("send", data);
        this.connector.Send([data], (ret) => {
            console.log(ret);
            if (callback != undefined) {
                callback(ret);
            }
        }, (err) => {
            console.log(err);
            if (errcallback != undefined) {
                errcallback(err);
            }
            errcallback(err);
        });
    }



    sendnoend(command: any[], callback = undefined, errcallback = undefined) {
        var data: any[] = [];
        data.push(0x5A);
        data.push(0xA5);

        for (let item of command) {
            data.push(item);
        }
        console.log("send", data);
        this.connector.Send([data], (ret) => {
            console.log(ret);
            if (callback != undefined) {
                callback(ret);
            }
        }, (err) => {
            console.log(err);
            if (errcallback != undefined) {
                errcallback(err);
            }
            errcallback(err);
        });
    }

    close() {
        this.connector.Close();
    }


}