import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DebtsComponent} from "./debts/debts.component";
import {ExampleComponent} from "./example/example.component";
import {SigninComponent} from "./signin/signin.component";

export const routes: Routes = [
  {path: 'debts', component: DebtsComponent},
  {path: 'example', component: ExampleComponent},
  {path: 'signin', component: SigninComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
