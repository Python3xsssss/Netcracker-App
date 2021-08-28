import {NgModule} from "@angular/core";
import {ExtraOptions, RouterModule, Routes} from '@angular/router';

import {HomeComponent} from "./view-controller/home/home.component";
import {AddUserComponent} from "./view-controller/user/add-user/add-user.component";
import {FormTeamComponent} from "./view-controller/team/form-team/form-team.component";
import {FormSkillComponent} from "./view-controller/skill/form-skill/form-skill.component";
import {ShowTeamComponent} from "./view-controller/team/show-team/show-team.component";
import {ListUserComponent} from "./view-controller/user/list-user/list-user.component";
import {ListTeamComponent} from "./view-controller/team/list-team/list-team.component";
import {ListSkillComponent} from "./view-controller/skill/list-skill/list-skill.component";
import {ShowUserComponent} from "./view-controller/user/show-user/show-user.component";
import {LoginComponent} from "./view-controller/login/login.component";
import {ShowReportComponent} from "./view-controller/team/show-report/show-report.component";
import {FormDepartComponent} from "./view-controller/department/form-depart/form-depart.component";


const routerOptions: ExtraOptions = {
  scrollPositionRestoration: 'enabled',
  anchorScrolling: 'enabled',
  scrollOffset: [0, 64],
};

const ROUTES: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'auth/sign-in', component: LoginComponent},

  {path: 'user/:id', component: ShowUserComponent},
  {path: 'users', component: ListUserComponent},
  {path: 'add-user', component: AddUserComponent},

  {path: 'add-depart', component: FormDepartComponent},
  {path: 'update-depart/:id', component: FormDepartComponent},

  {path: 'team/:id', component: ShowTeamComponent},
  {path: 'team/:id/show-report', component: ShowReportComponent},
  {path: 'teams', component: ListTeamComponent},
  {path: 'add-team', component: FormTeamComponent},
  {path: 'update-team/:id', component: FormTeamComponent},

  {path: 'skills', component: ListSkillComponent},
  {path: 'add-skill', component: FormSkillComponent},
  {path: 'update-skill/:id', component: FormSkillComponent},

  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: '**', redirectTo: 'home'}
];

@NgModule({
  imports: [RouterModule.forRoot(ROUTES, routerOptions)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
