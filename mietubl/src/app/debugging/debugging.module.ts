import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DebuggingPageRoutingModule } from './debugging-routing.module';

import { DebuggingPage } from './debugging.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DebuggingPageRoutingModule
  ],
  declarations: [DebuggingPage]
})
export class DebuggingPageModule {}
