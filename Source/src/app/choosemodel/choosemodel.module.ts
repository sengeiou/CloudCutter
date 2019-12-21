import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ChoosemodelPageRoutingModule } from './choosemodel-routing.module';

import { ChoosemodelPage } from './choosemodel.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ChoosemodelPageRoutingModule
  ],
  declarations: [ChoosemodelPage]
})
export class ChoosemodelPageModule {}
