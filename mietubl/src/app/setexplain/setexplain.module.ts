import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SetexplainPageRoutingModule } from './setexplain-routing.module';

import { SetexplainPage } from './setexplain.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SetexplainPageRoutingModule
  ],
  declarations: [SetexplainPage]
})
export class SetexplainPageModule {}
