import { IConnector } from './IConnector';

export class Sender{


    public constructor(public connector:IConnector){
        connector.SetReadHandle(this.read);
    }


    static READSPEED=[0xAA,0x10];
    static READBLADEPRESS=[0xAA,0x11];
    static READGEARRATE=[0xAA,0x12];
    static READMACHINEID=[0xAA,0x13];

    static SETSPEED=[0xBB,0x10];
    static SETBLADEPRESS=[0xBB,0x11];
    static SETGEARRATE=[0xBB,0x12];
    static SETMACHINEID=[0xBB,0x13];


    static READMACHINESTATUS=[0xAA,0x20];
    static WRITEFILE=[0xCC,0x30];

    readSpeed(){
        var data:any[]=[];
        data.push(Sender.READSPEED[0]);
        data.push(0x00);
        data.push(Sender.READSPEED[1]);
        data.push(0x00);
        data.push(0x00);
        return this.send(data);
    }
    setSpeed(speed){
        var data:any[]=[];
        data.push(Sender.SETSPEED[0]);
        data.push(0x00);
        data.push(Sender.SETSPEED[1]);
        data.push(0x02);
        data.push(0x00);
        data.push(speed);
        data.push(speed);
        return this.send(data);
    }


    send(command:any[]){
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
        this.connector.Send(command)
    }
    read(source,data,e){
        
    }

}