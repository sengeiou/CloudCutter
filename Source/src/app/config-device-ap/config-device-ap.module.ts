import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ConfigDeviceAPPageRoutingModule } from './config-device-ap-routing.module';

import { ConfigDeviceAPPage } from './config-device-ap.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ConfigDeviceAPPageRoutingModule
  ],
  declarations: [ConfigDeviceAPPage]
})
export class ConfigDeviceAPPageModule {}
