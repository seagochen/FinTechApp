import { Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LedgerKeeperComponent} from "./ledger-keeper/ledger-keeper.component";
import {AccountKeeperComponent} from "./account-keeper/account-keeper.component";

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, // 重定向到home组件
  { path: 'home', component: HomeComponent },
  { path: 'account-keeper', component: AccountKeeperComponent },
  { path: 'ledger-keeper', component: LedgerKeeperComponent },
];
