import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DebuggingPage } from './debugging.page';

const routes: Routes = [
  {
    path: '',
    component: DebuggingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DebuggingPageRoutingModule {}
