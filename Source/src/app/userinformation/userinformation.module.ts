import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UserinformationPageRoutingModule } from './userinformation-routing.module';

import { UserinformationPage } from './userinformation.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UserinformationPageRoutingModule
  ],
  declarations: [UserinformationPage]
})
export class UserinformationPageModule {}
