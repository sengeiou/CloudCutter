import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { MyaccountPageRoutingModule } from './myaccount-routing.module';

import { MyaccountPage } from './myaccount.page';

//  import { NgZorroAntdModule } from 'ng-zorro-antd';
import {RoundProgressModule} from'angular-svg-round-progressbar';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
  RoundProgressModule,
    MyaccountPageRoutingModule
  ],
  declarations: [MyaccountPage]
})
export class MyaccountPageModule {}
