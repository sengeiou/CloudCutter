export interface IConnector{
    Send(command:any[]);
    SetReadHandle(handle);
    IsConnected():boolean;
    addlog(item:string);
    showlog();
}