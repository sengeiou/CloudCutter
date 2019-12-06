import { IConnector } from './IConnector';

export class Socket implements IConnector {
    readhandle = null;
    sockclient: WebSocket = null;

    logs = [];

    constructor(public IP: string, public Port: String) {

    }



    Send(commandlist: any[], callback,errcallback) {
        //command.

        var sockclient:WebSocket=this.sockclient;
        if(sockclient==null){

            var ws = "ws://" + this.IP + ":" + this.Port;
            sockclient = new WebSocket(ws);
        }
        sockclient.onopen = () => {
            // Web Socket 已连接上，使用 send() 方法发送数据
            for (let command of commandlist) {
                var v = new Int8Array(command.length);
                for (var i = 0; i < command.length; i++) {
                    v[i] = command[i];
                }

                sockclient.send(v);
            }
        };
        sockclient.onmessage = (e) => {
            // Web Socket 已连接上，使用 send() 方法发送数据
            //this.Read(e.data);
            //this.addlog("[info]:socket info:"+e.source+"~"+e.data);
            //if (this.readhandle != null) {
            //    this.readhandle(e.source, e.data, e);
            //}
            callback({ source: e.data, data: e.data, event: e });

        };
        sockclient.onerror = (e) => {
            //this.addlog("[error]:" + e);
            errcallback(e);
        };

        this.sockclient=sockclient;
    }


    SendNext(commandlist: any[]) {
        //command.


        for (let command of commandlist) {
            var v = new Int8Array(command.length);
            for (var i = 0; i < command.length; i++) {
                v[i] = command[i];
            }

            this.sockclient.send(v);
        }
    }

    close(){
        if(this.sockclient!=null){
            this.sockclient.close();
        }
    }

    static GetSocketList(ip="", port,callback) {
        var ipexp=ip.split(".");
        var iphead=ipexp[0]+"."+ipexp[1]+"."+ipexp[2]+".";
        var count=0;
        var result=[];
        for(let i=1;i<=255;i++){
            var targetip=iphead+i.toString();
            Socket.TryConnect(targetip,port,(ret)=>{
                count++;
                if(ret.connected==true){
                    result.push(ret);
                }
                if(count>=255){
                    callback(result);
                }
            });
        }
    }

    static TryConnect(ip,port,callback){
        var sockclient=new Socket(ip,port);
        var connected=false;
        sockclient.Send([[1]],(ret)=>{
            connected=true;
            callback({ip,port,connected});
        },(e)=>{
            callback({ip,port,connected});
            sockclient.close();
        });

        setTimeout(()=>{
            if(connected==false){
                callback({ip,port,connected});
            }
            sockclient.close();
        },1000);
    }

}