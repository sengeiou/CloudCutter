import { Component, OnInit } from '@angular/core';
import { Network } from '@ionic-native/network/ngx';
import { NetworkInterface } from '@ionic-native/network-interface/ngx';

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
      this.address=address;
    })
    .catch(error => alert(`Unable to get IP: ${error}`));
  }
  getCarrier(){
    
    this.networkInterface.getCarrierIPAddress()
    .then(address => {
      this.carrier=address;
    })
    .catch(error => alert(`Unable to get IP: ${error}`));
  }

  setappassword(){

  }

  starttoscan(){
    
  }


}
