import { IConnector } from './IConnector';

declare let CordovaWebsocketPlugin: any;

export class AdvanceSocket implements IConnector {
    readhandle = null;
    sockclient: WebSocket = null;

    logs = [];

    wsOptions=null;

    constructor(public IP: string, public Port: String) {
        var accessToken = "abcdefghiklmnopqrstuvwxyz";
        this.wsOptions = {
            url: "ws://"+IP+":"+Port,
            timeout: 5000,
            pingInterval: 10000,
            headers: {"Authorization": "Bearer " + accessToken},
            acceptAllCerts: false
        }
    }



    Send(commandlist: any[], callback,errcallback) {
        //command.


        CordovaWebsocketPlugin.wsConnect(this.wsOptions,
            function(recvEvent) {
                alert("Received callback from WebSocket: "+recvEvent["callbackMethod"]);
            },
            function(success) {

                alert("Connected to WebSocket with id: " + success.webSocketId);
                for (let command of commandlist) {
                    var v = new Int8Array(command.length);
                    for (var i = 0; i < command.length; i++) {
                        v[i] = command[i];
                    }
                    CordovaWebsocketPlugin.wsSend(success.webSocketId, v);
                    //sockclient.send(v);
                }
                //console.log();
            },
            function(error) {
                alert("Failed to connect to WebSocket: "+
                            "code: "+error["code"]+
                            ", reason: "+error["reason"]+
                            ", exception: "+error["exception"]);
            }
        );

    }



    Close(){
    }

}