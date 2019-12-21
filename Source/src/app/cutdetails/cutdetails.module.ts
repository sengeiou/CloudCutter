import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CutdetailsPageRoutingModule } from './cutdetails-routing.module';

import { CutdetailsPage } from './cutdetails.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CutdetailsPageRoutingModule
  ],
  declarations: [CutdetailsPage]
})
export class CutdetailsPageModule {}
