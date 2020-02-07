import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpModule } from '@angular/http';
import { HTTP } from '@ionic-native/http/ngx';
import { NetworkInterface } from '@ionic-native/network-interface/ngx';

import {RoundProgressModule} from'angular-svg-round-progressbar';
import { QRCodeModule } from 'angular2-qrcode';
import { AppUpdate } from '@ionic-native/app-update/ngx';
import { Device } from '@ionic-native/device/ngx';
import { AppVersion } from '@ionic-native/app-version/ngx';

@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [ RoundProgressModule, BrowserModule, IonicModule.forRoot({ 
    mode: 'ios',
    rippleEffect: true,
    scrollAssist: false 
  }), AppRoutingModule, HttpModule],
  providers: [
    StatusBar,
    SplashScreen,
    HTTP,
    NetworkInterface,
    QRCodeModule,
    AppUpdate,
    Device,
    AppVersion,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
