import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CutdetailsPage } from './cutdetails.page';

const routes: Routes = [
  {
    path: '',
    component: CutdetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CutdetailsPageRoutingModule {}
