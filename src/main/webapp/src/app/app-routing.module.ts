import {NgModule} from "@angular/core";
import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from "./view-controller/home/home.component";
import {AddUserComponent} from "./view-controller/user/add-user/add-user.component";
import {AddDepartComponent} from "./view-controller/department/add-depart/add-depart.component";
import {AddTeamComponent} from "./view-controller/team/add-team/add-team.component";
import {AddSkillComponent} from "./view-controller/skill/add-skill/add-skill.component";
import {ShowTeamComponent} from "./view-controller/team/show-team/show-team.component";
import {ListUserComponent} from "./view-controller/user/list-user/list-user.component";
import {ListTeamComponent} from "./view-controller/team/list-team/list-team.component";
import {ListSkillComponent} from "./view-controller/skill/list-skill/list-skill.component";


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'add-user', component: AddUserComponent},
  {path: 'add-depart', component: AddDepartComponent},
  {path: 'add-team', component: AddTeamComponent},
  {path: 'add-skill', component: AddSkillComponent},
  {path: 'team', component: ShowTeamComponent},
  {path: 'users', component: ListUserComponent},
  {path: 'teams', component: ListTeamComponent},
  {path: 'skills', component: ListSkillComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
