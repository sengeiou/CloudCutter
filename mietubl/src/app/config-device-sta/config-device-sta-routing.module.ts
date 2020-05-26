import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ConfigDeviceSTAPage } from './config-device-sta.page';

const routes: Routes = [
  {
    path: '',
    component: ConfigDeviceSTAPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ConfigDeviceSTAPageRoutingModule {}
