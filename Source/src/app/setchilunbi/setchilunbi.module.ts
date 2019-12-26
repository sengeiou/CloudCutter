import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SetchilunbiPageRoutingModule } from './setchilunbi-routing.module';

import { SetchilunbiPage } from './setchilunbi.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SetchilunbiPageRoutingModule
  ],
  declarations: [SetchilunbiPage]
})
export class SetchilunbiPageModule {}
