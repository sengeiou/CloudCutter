import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SetdaoyaPageRoutingModule } from './setdaoya-routing.module';

import { SetdaoyaPage } from './setdaoya.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SetdaoyaPageRoutingModule
  ],
  declarations: [SetdaoyaPage]
})
export class SetdaoyaPageModule {}
