import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { WifiselectPageRoutingModule } from './wifiselect-routing.module';

import { WifiselectPage } from './wifiselect.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    WifiselectPageRoutingModule
  ],
  declarations: [WifiselectPage]
})
export class WifiselectPageModule {}
