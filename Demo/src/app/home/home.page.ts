import { Component, OnInit } from '@angular/core';
import { Network } from '@ionic-native/network/ngx';
import { NetworkInterface } from '@ionic-native/network-interface/ngx';
import { Socket } from 'src/DataMgr/Socket';
import { Sender } from 'src/DataMgr/Sender';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  constructor(public network: Network,public networkInterface: NetworkInterface) { }


  ngOnInit(): void {
  }

  address=null;
  carrier=null;
  apwifiname="";
  apwifipassword="";


  ionViewDidEnter() {
    
  }

  getWifi(){
    
    this.networkInterface.getWiFiIPAddress()
    .then(address => {
      alert(JSON.stringify(address));
      this.address=address;
    })
    .catch(error => alert(`Unable to get IP: ${error}`));
  }
  getCarrier(){
    
    this.networkInterface.getCarrierIPAddress()
    .then(address => {
      alert(JSON.stringify(address));
      this.carrier=address;
    })
    .catch(error => alert(`Unable to get IP: ${error}`));
  }
  getapinfo(){
    var socket=new Socket("192.168.10.20","5000");
    var sender=new Sender(socket);
    sender.readAPInfo((ret)=>{},()=>{});
  }

  setappassword(){
    
  }

  starttoscan(){
    
  }


}
