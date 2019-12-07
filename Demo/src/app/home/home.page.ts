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

  constructor(public network: Network, public networkInterface: NetworkInterface) { }
  apaddress = "192.168.10.20";
  allstawifilist = [];
  currentmachineip = "127.0.0.1";
  ngOnInit(): void {
  }

  address = null;
  carrier = null;
  apwifiname = "";
  apwifipassword = "";


  ionViewDidEnter() {

  }

  getWifi() {

    this.networkInterface.getWiFiIPAddress()
      .then(address => {
        alert(JSON.stringify(address));
        this.address = address;
      })
      .catch(error => alert(`Unable to get IP: ${error}`));
  }
  getCarrier() {

    this.networkInterface.getCarrierIPAddress()
      .then(address => {
        alert(JSON.stringify(address));
        this.carrier = address;
      })
      .catch(error => alert(`Unable to get IP: ${error}`));
  }
  getapinfo() {
    var socket = new Socket(this.apaddress, "5000");
    var sender = new Sender(socket);
    sender.readAPInfo((ret) => { }, () => { });
  }

  setappassword() {

  }

  starttoscan() {
    Socket.GetSocketList(this.address.ip, 5000, (ret) => {
      this.allstawifilist = ret;
    });
  }

  getSTAWIFI() {
    var socket = new Socket(this.apaddress, "5000");
    var sender = new Sender(socket);
    sender.readSTAInfo((ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  wifiname = "";
  wifipassword = "";
  setSTAWIFI() {

    var socket = new Socket(this.apaddress, "5000");
    var sender = new Sender(socket);
    sender.setSTAInfo(this.wifiname, this.wifipassword, (ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }


  getSpeed() {
    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.readSpeed((ret) => {
      alert(JSON.stringify(ret));
      sender.close();
    }, () => { });
  }
  getBlade() {
    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.readBladePressure((ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  getGear() {
    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.readGearRate((ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  getMACHINE() {

    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.readMachineID((ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  getSTATUS() {
    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.readMachineStatus((ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  speed = 0;
  blade = 0;
  xgear = 0;
  ygear = 0;
  setSpeed() {
    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    alert(1);
    sender.setSpeed(this.speed, (ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  setBlade() {
    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.setBladePressure(this.blade, (ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  setGear() {
    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.setGearRate(this.xgear, this.ygear, (ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  machineid = "aaa";
  setMACHINE() {
    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.setMachineID(this.machineid, (ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }

  tryCut(){

    if (this.currentmachineip == "") {
      alert("请先查找设备，然后设置设备");
      return;
    }
    var socket = new Socket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.tryCuy((ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }

}
