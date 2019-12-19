import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AboutnativePage } from './aboutnative.page';

const routes: Routes = [
  {
    path: '',
    component: AboutnativePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AboutnativePageRoutingModule {}
