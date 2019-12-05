import { IConnector } from './IConnector';

export class Socket implements IConnector {
    readhandle=null;
    sockclient:WebSocket=null;

    logs=[];

    constructor(IP:string,Port:String){
        var ws="ws://"+IP+":"+Port;
        var sockclient=new WebSocket(ws);
        this.sockclient=sockclient;
        this.sockclient.onopen= ()=>
        {
           // Web Socket 已连接上，使用 send() 方法发送数据
           this.addlog("[info]:socket open:"+ws);
        };
        this.sockclient.onmessage= (e)=>
        {
           // Web Socket 已连接上，使用 send() 方法发送数据
           //this.Read(e.data);
           this.addlog("[info]:socket info:"+e.source+"~"+e.data);
            if(this.readhandle!=null){
                this.readhandle(e.source,e.data,e);
            }

        };
        this.sockclient.onerror=(e)=>{
           this.addlog("[error]:"+e);
        };
    }

    addlog(log){
        this.logs.push(log);
    }
    showlog() {
        console.log("socketlog",this.logs);
    }

    
    IsConnected(): boolean {
        return this.sockclient.OPEN==0;
    }

    Send(command: any[]) {
        //command.
        var v=new Int8Array(command.length);
        for(var i=0;i<command.length;i++){
            v[i]=command[i];
        }
        this.sockclient.send(v);
    }
    SetReadHandle(handle: any) {
        this.readhandle=handle;
    }

}