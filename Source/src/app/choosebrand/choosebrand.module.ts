import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ChoosebrandPageRoutingModule } from './choosebrand-routing.module';

import { ChoosebrandPage } from './choosebrand.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ChoosebrandPageRoutingModule
  ],
  declarations: [ChoosebrandPage]
})
export class ChoosebrandPageModule {}
