import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DebtsComponent} from './debts/debts.component';
import {HomeComponent} from './home/home.component';
// import {SigninComponent} from './signin/signin.component';
import {LoginComponent} from "./login/login.component";

export const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'home'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'debts', component: DebtsComponent},
  {path: 'example', component: HomeComponent},
  // {path: 'signin', component: SigninComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
