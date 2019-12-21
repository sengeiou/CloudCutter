import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { EquipmentPageRoutingModule } from './equipment-routing.module';

import { EquipmentPage } from './equipment.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EquipmentPageRoutingModule
  ],
  declarations: [EquipmentPage]
})
export class EquipmentPageModule {}
