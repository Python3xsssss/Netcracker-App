import {NgModule} from "@angular/core";
import {ExtraOptions, RouterModule, Routes} from '@angular/router';

import {HomeComponent} from "./view-controller/home/home.component";
import {AddUserComponent} from "./view-controller/user/add-user/add-user.component";
import {AddDepartComponent} from "./view-controller/department/add-depart/add-depart.component";
import {AddTeamComponent} from "./view-controller/team/add-team/add-team.component";
import {AddSkillComponent} from "./view-controller/skill/add-skill/add-skill.component";
import {ShowTeamComponent} from "./view-controller/team/show-team/show-team.component";
import {ListUserComponent} from "./view-controller/user/list-user/list-user.component";
import {ListTeamComponent} from "./view-controller/team/list-team/list-team.component";
import {ListSkillComponent} from "./view-controller/skill/list-skill/list-skill.component";
import {ShowUserComponent} from "./view-controller/user/show-user/show-user.component";
import {UpdateUserComponent} from "./view-controller/user/update-user/update-user.component";
import {LoginComponent} from "./view-controller/login/login.component";
import {UpdateDepartComponent} from "./view-controller/department/update-depart/update-depart.component";
import {ShowReportComponent} from "./view-controller/team/show-report/show-report.component";


const routerOptions: ExtraOptions = {
  scrollPositionRestoration: 'enabled',
  anchorScrolling: 'enabled',
  scrollOffset: [0, 64],
};

const ROUTES: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},

  {path: 'user/:id', component: ShowUserComponent},
  {path: 'add-user', component: AddUserComponent},
  {path: 'update-user/:id', component: UpdateUserComponent},
  {path: 'add-depart', component: AddDepartComponent},
  {path: 'update-depart/:id', component: UpdateDepartComponent},
  {path: 'add-team', component: AddTeamComponent},
  {path: 'add-skill', component: AddSkillComponent},
  {path: 'team/:id', component: ShowTeamComponent},
  {path: 'team/:id/show-report', component: ShowReportComponent},
  {path: 'users', component: ListUserComponent},
  {path: 'teams', component: ListTeamComponent},
  {path: 'skills', component: ListSkillComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(ROUTES, routerOptions)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
