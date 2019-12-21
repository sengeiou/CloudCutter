import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ChoosebrandPage } from './choosebrand.page';

const routes: Routes = [
  {
    path: '',
    component: ChoosebrandPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChoosebrandPageRoutingModule {}
