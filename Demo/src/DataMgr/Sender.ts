import { IConnector } from './IConnector';
import { callbackify } from 'util';
import { unwatchFile } from 'fs';

export class Sender{


    public constructor(public connector:IConnector){
    }


    static READSPEED=[0xAA,0x10];
    static READBLADEPRESS=[0xAA,0x11];
    static READGEARRATE=[0xAA,0x12];
    static READMACHINEID=[0xAA,0x13];
    static READAPINFO=[0xAA,0x14];
    static READWIFI=[0xAA,0x15];

    static SETSPEED=[0xBB,0x10];
    static SETBLADEPRESS=[0xBB,0x11];
    static SETGEARRATE=[0xBB,0x12];
    static SETMACHINEID=[0xBB,0x13];
    static SETAPINFO=[0xBB,0x14];
    static SETWIFI=[0xBB,0x15];


    static READMACHINESTATUS=[0xAA,0x20];
    static WRITEFILE=[0xCC,0x30];

    readSpeed(callback,errcallback){
        var data:any[]=[];
        data.push(Sender.READSPEED[0]);
        data.push(0x00);
        data.push(Sender.READSPEED[1]);
        data.push(0x00);
        data.push(0x00);
        return this.send(data,callback,errcallback);
    }
    setSpeed(speed,callback,errcallback){
        var data:any[]=[];
        data.push(Sender.SETSPEED[0]);
        data.push(0x00);
        data.push(Sender.SETSPEED[1]);
        data.push(0x02);
        data.push(0x00);
        data.push(speed);
        data.push(speed);
        return this.send(data,callback,errcallback);
    }

    readAPInfo(callback,errcallback){
        var data:any[]=[];
        data.push(Sender.READAPINFO[0]);
        data.push(0x00);
        data.push(Sender.READAPINFO[1]);
        data.push(0x00);
        data.push(0x00);
        return this.send(data,(ret)=>{
            var result={
                machinestatus:ret[7],
                resultcode:ret[8],
                wifiname:this.getString(ret.slice(9,25)),
                wifipassword:this.getString(ret.slice(25,41)),
                ip:this.getIpAddress(ret.slice(41,45)),
                subnet:this.getIpAddress(ret.slice(45,49)),
                port:this.getNumber(ret[49],ret[50],ret[51],ret[52]),
            };
            callback(result);
        },errcallback);
    }
    setAPInfo(wifiname,wifipassword,ip,subnet,port,callback,errcallback){
        var data:any[]=[];
        data.push(Sender.SETAPINFO[0]);
        data.push(0x00);
        data.push(Sender.SETAPINFO[1]);
        data.push(0x08);
        data.push(0x00);
        data.concat(this.convertString(wifiname,16));
        data.concat(this.convertString(wifipassword,16));
        data.concat(this.convertIpAddress(ip));
        data.concat(this.convertIpAddress(subnet));
        data.concat(this.convertNumber(port));
        return this.send(data,(ret)=>{
            var result={
                machinestatus:ret[7],
                resultcode:ret[8]
            };
            callback(result);
        },errcallback);
    }
    convertNumber(num:number) {
        var a=num.toString(16);
        var d1=parseInt("0x"+a.substr(2,2));//88
        var d2=parseInt("0x"+a.substr(0,2));//13
        return [d1,d2,0,0];
    }
    convertString(wifiname: string, num: number): any {
        var ret=[];
        for(var i=0;i<num;i++){
            if(wifiname.length>i){
                ret.push(wifiname[i].charCodeAt(0));
            }else{
                ret.push(0x00);
            }
        }
    }
    convertIpAddress(ip: string): any {
        var ret=[];
        var ipexp=ip.split(".");
        for(let st of ipexp){
            ret.push(parseInt(st));
        }
        return ret;
        
    }
    getNumber(d1: any, d2: any, d3: any, d4: any) {
        return parseInt("0x"+d4.toString(16)+d3.toString(16)+d2.toString(16)+d1.toString(16));
    }
    getIpAddress(data: any) {
        var ret=[];
        for(let d of data){
            ret.push(d);
        }
        return ret.join(".");
    }

    getString(data: []) {
        var ret="";
        for(let d of data){
            if(d!=0x00){
                ret+=String.fromCharCode(d);
            }
        }
        return ret;
    }

    


    send(command:any[],callback=undefined,errcallback=undefined){
        var data:any[]=[];
        data.push(0x5A);
        data.push(0xA5);
        
        var d=0x00;
        for(let item of command){
            data.push(item);
            d+=item;
        }
        d=d&0xff;
        data.push(d);
        data.push(0x0D);
        data.push(0x0A);
        console.log("send",data);
        this.connector.Send([command],(ret)=>{
            console.log(ret);
            if(callback!=undefined){
                callback(ret);
            }
        },(err)=>{
            console.log(err);
            if(errcallback!=undefined){
                errcallback(err);
            }
            errcallback(err);
        });
    }

    

}