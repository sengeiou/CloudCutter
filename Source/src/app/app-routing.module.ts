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
  {
    path: 'config-device',
    loadChildren: () => import('./config-device/config-device.module').then( m => m.ConfigDevicePageModule)
  },
  {
    path: 'config-device-ap',
    loadChildren: () => import('./config-device-ap/config-device-ap.module').then( m => m.ConfigDeviceAPPageModule)
  },
  {
    path: 'config-device-sta',
    loadChildren: () => import('./config-device-sta/config-device-sta.module').then( m => m.ConfigDeviceSTAPageModule)
  },
  {
    path: 'setdaoya',
    loadChildren: () => import('./setdaoya/setdaoya.module').then( m => m.SetdaoyaPageModule)
  },
  {
    path: 'setchilunbi',
    loadChildren: () => import('./setchilunbi/setchilunbi.module').then( m => m.SetchilunbiPageModule)
  },
  {
    path: 'debugging',
    loadChildren: () => import('./debugging/debugging.module').then( m => m.DebuggingPageModule)
  },
  {
    path: 'debugging-list',
    loadChildren: () => import('./debugging-list/debugging-list.module').then( m => m.DebuggingListPageModule)
  },
  {
    path: 'wifiselect',
    loadChildren: () => import('./wifiselect/wifiselect.module').then( m => m.WifiselectPageModule)
  },  {
    path: 'register',
    loadChildren: () => import('./register/register.module').then( m => m.RegisterPageModule)
  },
  {
    path: 'forgetpwd',
    loadChildren: () => import('./forgetpwd/forgetpwd.module').then( m => m.ForgetpwdPageModule)
  },
  {
    path: 'qrcodescan',
    loadChildren: () => import('./qrcodescan/qrcodescan.module').then( m => m.QrcodescanPageModule)
  },
  {
    path: 'authlogin',
    loadChildren: () => import('./authlogin/authlogin.module').then( m => m.AuthloginPageModule)
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
