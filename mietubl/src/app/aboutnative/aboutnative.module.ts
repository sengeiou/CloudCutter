import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AboutnativePageRoutingModule } from './aboutnative-routing.module';

import { AboutnativePage } from './aboutnative.page';
import { QRCodeModule } from 'angular2-qrcode';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    QRCodeModule,
    AboutnativePageRoutingModule
  ],
  declarations: [AboutnativePage]
})

export class AboutnativePageModule {}
