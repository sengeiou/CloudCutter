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
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
