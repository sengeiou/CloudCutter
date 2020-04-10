import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SetallPage } from './setall.page';

const routes: Routes = [
  {
    path: '',
    component: SetallPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SetallPageRoutingModule {}
