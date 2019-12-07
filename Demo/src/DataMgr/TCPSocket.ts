import { IConnector } from './IConnector';

declare let Socket: any;

export class TCPSocket implements IConnector {
    readhandle = null;

    logs = [];

    socket = null;

    wsOptions = null;

    constructor(public IP: string, public Port: String) {
    }



    Send(commandlist: any[], callback, errcallback) {
        //command.

        var unopen = this.socket == null;

        if (unopen) {
            this.socket = new Socket();
        }

        this.socket.onData = function (data) {
            //alert("onData");
            // invoked after new batch of data is received (typed array of bytes Uint8Array)
            //alert(data);

            callback(data);
        };
        this.socket.onError = function (errorMessage) {
            // invoked after error occurs during connection
            alert("onError");
            alert(errorMessage);
            errcallback(errorMessage);
        };
        this.socket.onClose = function (hasError) {
            // invoked after connection close
            alert("onClose");
            alert(hasError);
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
                        alert(JSON.stringify(v));
                        this.socket.write(v);
                        //sockclient.send(v);
                    }
                },
                (errorMessage) => {
                    alert("openerrorMessage");
                    // invoked after unsuccessful opening of socket
                    alert(errorMessage);
                });
        } else {
            for (let command of commandlist) {
                var v = new Uint8Array(command.length);
                for (var i = 0; i < command.length; i++) {
                    v[i] = command[i];
                }
                alert(JSON.stringify(v));
                this.socket.write(v);
                //sockclient.send(v);
            }
        }
    }



    Close() {
        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
        }
    }

}