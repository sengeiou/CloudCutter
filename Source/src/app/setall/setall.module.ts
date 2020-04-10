import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SetallPageRoutingModule } from './setall-routing.module';

import { SetallPage } from './setall.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SetallPageRoutingModule
  ],
  declarations: [SetallPage]
})
export class SetallPageModule {}
