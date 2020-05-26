import { IConnector } from './IConnector';

declare let Socket: any;

export class TCPSocket implements IConnector {
    readhandle = null;

    logs = [];

    socket = null;

    wsOptions = null;

    pltlist = [];

    constructor(public IP: string, public Port: String) {
    }

    SendForText(straa: String, callback) {
        var socket = new Socket();
        socket.onData = function (data) {
            //console.log("onData");
            // invoked after new batch of data is received (typed array of bytes Uint8Array)
            //console.log(data);
            console.log("receive", data);
            //5aa5
            if (data[0] == 0x5a && data[1] == 0xa5) {
                console.log(straa);
                var v = new Uint8Array(straa.length);
                for (var i = 0; i < straa.length; i++) {
                    v[i] = straa[i].charCodeAt(0);
                }
                console.log("发送的数据：" + JSON.stringify(v));
                socket.write(v);
            } else {
                var str = "";
                for (let item of data) {
                    str += String.fromCharCode(item);
                }
                //alert(str);
                callback(str);
                socket.close();
            }
            //socket.close();
        };
        socket.onError = function (errorMessage) {
            // invoked after error occurs during connection
            //alert("返回错误" + errorMessage);
            socket.close();
            console.log(errorMessage);
            callback("ERR|FAIL");
        };
        socket.onClose = function (hasError) {
            // invoked after connection close
            console.log("关闭:" + hasError);
        };


        socket.open(
            this.IP,
            this.Port,
            () => {
                // invoked after successful opening of socket
            },
            (errorMessage) => {
                console.log("无法连接远程服务器:" + errorMessage);
                callback("ERR|CONNFAIL");
            });
    }



    Send(commandlist: any[], callback, errcallback) {
        //command.

        var unopen = this.socket == null;

        if (unopen) {
            this.socket = new Socket();
        }

        this.socket.onData = function (data) {
            //console.log("onData");
            // invoked after new batch of data is received (typed array of bytes Uint8Array)
            //console.log(data);

            callback(data);
        };
        this.socket.onError = function (errorMessage) {
            // invoked after error occurs during connection
            console.log("返回错误" + errorMessage);
            //console.log(errorMessage);
            errcallback(errorMessage);
        };
        this.socket.onClose = function (hasError) {
            // invoked after connection close
            console.log("关闭:" + hasError);
        };
        if (unopen) {

            this.socket.open(
                this.IP,
                this.Port,
                () => {
                    // invoked after successful opening of socket
                    for (let command of commandlist) {
                        var v = new Uint8Array(command.length);
                        for (var i = 0; i < command.length; i++) {
                            v[i] = command[i];
                        }
                        console.log("发送的数据：" + JSON.stringify(v));
                        this.socket.write(v);
                        //sockclient.send(v);
                    }
                },
                (errorMessage) => {
                    console.log("openerrorMessage");
                    // invoked after unsuccessful opening of socket
                    console.log("openerrorMessage" + errorMessage);
                });
        } else {
            for (let command of commandlist) {
                var v = new Uint8Array(command.length);
                for (var i = 0; i < command.length; i++) {
                    v[i] = command[i];
                }
                console.log(JSON.stringify(v));
                this.socket.write(v);
                //sockclient.send(v);
            }
        }
    }

    TestOpen(callback) {
        var ret = false;
        try {
            var socket = new Socket();
            socket.open(
                this.IP,
                this.Port,
                () => {
                    if (ret == false) {
                        ret = true;
                        callback({ status: true });
                        socket.close();
                    }
                },
                (errorMessage) => {
                    // invoked after unsuccessful opening of socket
                    console.log("openerrorMessage" + errorMessage);
                    if (ret == false) {
                        ret = true;
                        callback({ status: false, result: errorMessage });
                    }
                });


            setTimeout(() => {
                if (ret == false) {
                    ret = true;
                    callback({ status: false, result: "timeout" });
                }
            }, 5000)
        } catch (e) {
            if (ret == false) {
                ret = true;
                callback({ status: false, result: "noinapp" });
            }
        }
    }



    Close() {
        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
        }
    }


    static GetSocketList(ip = "", port, callback) {
        var ipexp = ip.split(".");
        var iphead = ipexp[0] + "." + ipexp[1] + "." + ipexp[2] + ".";
        var count = 0;
        var result = [];
        for (let i = 1; i <= 255; i++) {
            var targetip = iphead + i.toString();
            TCPSocket.TryConnect(targetip, port, (ret) => {
                count++;
                if (ret.connected == true) {
                    result.push(ret);
                }
                if (count >= 255) {
                    callback(result);
                }
            });
        }
    }



    static TryConnect(ip, port, callback) {
        var socket = new Socket();
        socket.open(
            ip,
            port,
            () => {
                // invoked after successful opening of socket
                callback({ ip, port, connected: true });
                socket.close();
            },
            (errorMessage) => {
                callback({ ip, port, connected: false });
            });
    }

}