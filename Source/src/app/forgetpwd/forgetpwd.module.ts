import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ForgetpwdPageRoutingModule } from './forgetpwd-routing.module';

import { ForgetpwdPage } from './forgetpwd.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ForgetpwdPageRoutingModule
  ],
  declarations: [ForgetpwdPage]
})
export class ForgetpwdPageModule {}
