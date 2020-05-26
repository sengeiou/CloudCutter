import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AgreementPageRoutingModule } from './agreement-routing.module';

import { AgreementPage } from './agreement.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AgreementPageRoutingModule
  ],
  declarations: [AgreementPage]
})
export class AgreementPageModule {}
