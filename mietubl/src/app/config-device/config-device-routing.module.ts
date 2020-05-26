import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ConfigDevicePage } from './config-device.page';

const routes: Routes = [
  {
    path: '',
    component: ConfigDevicePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ConfigDevicePageRoutingModule {}
