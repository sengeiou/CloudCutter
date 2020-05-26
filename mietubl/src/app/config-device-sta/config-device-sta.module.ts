import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ConfigDeviceSTAPageRoutingModule } from './config-device-sta-routing.module';

import { ConfigDeviceSTAPage } from './config-device-sta.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ConfigDeviceSTAPageRoutingModule
  ],
  declarations: [ConfigDeviceSTAPage]
})
export class ConfigDeviceSTAPageModule {}
