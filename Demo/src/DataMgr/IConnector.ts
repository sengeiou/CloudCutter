export interface IConnector{
    Send(command:any[],callback,errcallback);
    Close();
}