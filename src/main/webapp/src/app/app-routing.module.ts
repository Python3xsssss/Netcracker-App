import {NgModule} from "@angular/core";
import {RouterModule, Routes} from '@angular/router';

import {AddUserComponent} from "./user/add-user/add-user.component";
import {HomeComponent} from "./home/home.component";


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'add-user', component: AddUserComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
