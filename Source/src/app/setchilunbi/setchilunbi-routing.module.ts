import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SetchilunbiPage } from './setchilunbi.page';

const routes: Routes = [
  {
    path: '',
    component: SetchilunbiPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SetchilunbiPageRoutingModule {}
