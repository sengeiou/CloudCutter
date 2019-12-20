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
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then( m => m.LoginPageModule)
  },
  {
    path: 'userinformation',
    loadChildren: () => import('./userinformation/userinformation.module').then( m => m.UserinformationPageModule)
  },
  {
    path: 'myaccount',
    loadChildren: () => import('./myaccount/myaccount.module').then( m => m.MyaccountPageModule)
  },
  {
    path: 'recharge',
    loadChildren: () => import('./recharge/recharge.module').then( m => m.RechargePageModule)
  },
  {
    path: 'setting',
    loadChildren: () => import('./setting/setting.module').then( m => m.SettingPageModule)
  },
  {
    path: 'aboutnative',
    loadChildren: () => import('./aboutnative/aboutnative.module').then( m => m.AboutnativePageModule)
  },
  {
    path: 'agreement',
    loadChildren: () => import('./agreement/agreement.module').then( m => m.AgreementPageModule)
  },
  {
    path: 'opinion',
    loadChildren: () => import('./opinion/opinion.module').then( m => m.OpinionPageModule)
  },
  {
    path: 'languagesettings',
    loadChildren: () => import('./languagesettings/languagesettings.module').then( m => m.LanguagesettingsPageModule)
  },
  {
    path: 'equipment',
    loadChildren: () => import('./equipment/equipment.module').then( m => m.EquipmentPageModule)
  },
  {
    path: 'setwifi',
    loadChildren: () => import('./setwifi/setwifi.module').then( m => m.SetwifiPageModule)
  },
  {
    path: 'setexplain',
    loadChildren: () => import('./setexplain/setexplain.module').then( m => m.SetexplainPageModule)
  },
  // {
  //   path: 'tab4',
  //   loadChildren: () => import('./tab4/tab4.module').then( m => m.Tab4PageModule)
  // },
   
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
