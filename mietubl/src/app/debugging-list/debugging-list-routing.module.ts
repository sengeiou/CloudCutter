import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DebuggingListPage } from './debugging-list.page';

const routes: Routes = [
  {
    path: '',
    component: DebuggingListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DebuggingListPageRoutingModule {}
