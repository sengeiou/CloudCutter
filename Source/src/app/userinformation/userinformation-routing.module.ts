import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserinformationPage } from './userinformation.page';

const routes: Routes = [
  {
    path: '',
    component: UserinformationPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserinformationPageRoutingModule {}
