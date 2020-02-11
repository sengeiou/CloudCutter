import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AuthloginPageRoutingModule } from './authlogin-routing.module';

import { AuthloginPage } from './authlogin.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AuthloginPageRoutingModule
  ],
  declarations: [AuthloginPage]
})
export class AuthloginPageModule {}
