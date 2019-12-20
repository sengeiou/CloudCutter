import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SetwifiPageRoutingModule } from './setwifi-routing.module';

import { SetwifiPage } from './setwifi.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SetwifiPageRoutingModule
  ],
  declarations: [SetwifiPage]
})
export class SetwifiPageModule {}
