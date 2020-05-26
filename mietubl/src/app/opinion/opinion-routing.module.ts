import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { OpinionPage } from './opinion.page';

const routes: Routes = [
  {
    path: '',
    component: OpinionPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OpinionPageRoutingModule {}
