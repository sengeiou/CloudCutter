import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./tabs/tabs.module').then(m => m.TabsPageModule)
  },
  {
    path: 'choosebrand',
    loadChildren: () => import('./choosebrand/choosebrand.module').then( m => m.ChoosebrandPageModule)
  },
  {
    path: 'choosemodel',
    loadChildren: () => import('./choosemodel/choosemodel.module').then( m => m.ChoosemodelPageModule)
  },
  {
    path: 'cutdetails',
    loadChildren: () => import('./cutdetails/cutdetails.module').then( m => m.CutdetailsPageModule)
  },
  {
    path: 'search',
    loadChildren: () => import('./search/search.module').then( m => m.SearchPageModule)
  },
   
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
