import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DebuggingListPageRoutingModule } from './debugging-list-routing.module';

import { DebuggingListPage } from './debugging-list.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DebuggingListPageRoutingModule
  ],
  declarations: [DebuggingListPage]
})
export class DebuggingListPageModule {}
