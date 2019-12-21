import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SetexplainPage } from './setexplain.page';

const routes: Routes = [
  {
    path: '',
    component: SetexplainPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SetexplainPageRoutingModule {}
