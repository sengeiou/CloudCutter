import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LanguagesettingsPage } from './languagesettings.page';

const routes: Routes = [
  {
    path: '',
    component: LanguagesettingsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LanguagesettingsPageRoutingModule {}
