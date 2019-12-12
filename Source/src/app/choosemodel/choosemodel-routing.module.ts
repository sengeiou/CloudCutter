import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ChoosemodelPage } from './choosemodel.page';

const routes: Routes = [
  {
    path: '',
    component: ChoosemodelPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChoosemodelPageRoutingModule {}
