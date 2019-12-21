import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AgreementPage } from './agreement.page';

const routes: Routes = [
  {
    path: '',
    component: AgreementPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AgreementPageRoutingModule {}
