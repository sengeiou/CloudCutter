import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { LanguagesettingsPageRoutingModule } from './languagesettings-routing.module';

import { LanguagesettingsPage } from './languagesettings.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    LanguagesettingsPageRoutingModule
  ],
  declarations: [LanguagesettingsPage]
})
export class LanguagesettingsPageModule {}
