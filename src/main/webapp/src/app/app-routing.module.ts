import {NgModule} from "@angular/core";
import { RouterModule, Routes } from '@angular/router';

import {AddUserComponent} from "./user/add-user/add-user.component";
import {ListUserComponent} from "./user/list-user/list-user.component";
import {EditUserComponent} from "./user/edit-user/edit-user.component";


const routes: Routes = [
  { path: 'home', component: ListUserComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'add-user', component: AddUserComponent },
  { path: 'edit-user', component: EditUserComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
