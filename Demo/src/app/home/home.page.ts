import { Component, OnInit } from '@angular/core';
import { Network } from '@ionic-native/network/ngx';
import { NetworkInterface } from '@ionic-native/network-interface/ngx';
import { Socket } from 'src/DataMgr/Socket';
import { Sender } from 'src/DataMgr/Sender';
import * as io from 'socket.io-client';
import { AdvanceSocket } from 'src/DataMgr/AdvanceSocket';
import { TCPSocket } from 'src/DataMgr/TCPSocket';
import { TestPlt } from 'src/DataMgr/TESTPLT';
import { File } from '@ionic-native/file/ngx';





@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  constructor(public network: Network, public networkInterface: NetworkInterface, public file: File) { }
  apaddress = "192.168.10.20";
  allstawifilist = [];
  currentmachineip = "192.168.10.131";
  ngOnInit(): void {
    //var cip=window.localStorage.getItem("cip");
    //this.currentmachineip = cip;
  }

  test() {
    var socket = new TCPSocket("120.77.151.197", "6123");
    console.log(socket);
    socket.Send([[0x01]], (ret) => {
      alert(ret);
    }, (err) => {
      alert(err);
    });
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
    var socket = new TCPSocket(this.apaddress, "5000");
    var sender = new Sender(socket);
    sender.readAPInfo((ret) => { }, () => { });
  }

  setappassword() {

  }

  setap() {
    this.currentmachineip = "192.168.10.20";
  }

  starttoscan() {
    TCPSocket.GetSocketList(this.address.ip, 5000, (ret) => {
      alert(JSON.stringify(ret));
      this.allstawifilist = ret;
    });
  }

  getSTAWIFI() {
    var socket = new TCPSocket(this.apaddress, "5000");
    var sender = new Sender(socket);
    sender.readSTAInfo((ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  wifiname = "";
  wifipassword = "";
  setSTAWIFI() {

    var socket = new TCPSocket(this.apaddress, "5000");
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
    var socket = new TCPSocket(this.currentmachineip, "5000");
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
    var socket = new TCPSocket(this.currentmachineip, "5000");
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
    var socket = new TCPSocket(this.currentmachineip, "5000");
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
    var socket = new TCPSocket(this.currentmachineip, "5000");
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
    var socket = new TCPSocket(this.currentmachineip, "5000");
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
    var socket = new TCPSocket(this.currentmachineip, "5000");
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
    var socket = new TCPSocket(this.currentmachineip, "5000");
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
    var socket = new TCPSocket(this.currentmachineip, "5000");
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
    var socket = new TCPSocket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.setMachineID(this.machineid, (ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }

  tryCut() {

    // if (this.currentmachineip == "") {
    //   alert("请先查找设备，然后设置设备");
    //   return;
    // }

    var socket = new TCPSocket(this.currentmachineip, "5000");
    var sender = new Sender(socket);
    sender.tryCuy((ret) => {
      alert(JSON.stringify(ret));
    }, () => { });
  }
  cut(filepath) {
    // if (this.currentmachineip == "") {
    //   alert("请先查找设备，然后设置设备");
    //   return;
    // }
    this.file.readAsText(this.file.applicationDirectory + "/www/assets/files", filepath).then((res) => {
      alert(res);
      var socket = new TCPSocket(this.currentmachineip, "5000");
      var sender = new Sender(socket);
      sender.writeFile("test", res, (ret) => {
        alert(JSON.stringify(ret));
      }, () => { });
    });
  }
  pltlist = [];
  filescan() {

    //alert(JSON.stringify(this.file));
    this.file.listDir(this.file.applicationDirectory, "www/assets/files").then((res) => {
      console.log(res);
      this.pltlist = res;

    }, (err) => {
      console.log(err);
      alert(JSON.stringify(err));
    });
  }
}
