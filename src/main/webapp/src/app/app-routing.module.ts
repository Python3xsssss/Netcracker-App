import {NgModule} from "@angular/core";
import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from "./view-controller/home/home.component";
import {AddUserComponent} from "./view-controller/user/add-user/add-user.component";
import {AddDepartComponent} from "./view-controller/department/add-depart/add-depart.component";
import {AddTeamComponent} from "./view-controller/team/add-team/add-team.component";
import {AddSkillComponent} from "./view-controller/skill/add-skill/add-skill.component";


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'add-user', component: AddUserComponent},
  {path: 'add-depart', component: AddDepartComponent},
  {path: 'add-team', component: AddTeamComponent},
  {path: 'add-skill', component: AddSkillComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
