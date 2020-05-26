import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { WifiselectPage } from './wifiselect.page';

const routes: Routes = [
  {
    path: '',
    component: WifiselectPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class WifiselectPageRoutingModule {}
