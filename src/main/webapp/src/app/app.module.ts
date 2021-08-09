import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";

import {AppComponent} from './app.component';
import {HomeComponent} from './view-controller/home/home.component';
import {AddUserComponent} from './view-controller/user/add-user/add-user.component';
import {ListUserComponent} from './view-controller/user/list-user/list-user.component';
import {AddDepartComponent} from './view-controller/department/add-depart/add-depart.component';
import {ListDepartComponent} from './view-controller/department/list-depart/list-depart.component';
import {AddTeamComponent} from "./view-controller/team/add-team/add-team.component";
import {ListTeamComponent} from "./view-controller/team/list-team/list-team.component";
import {AddSkillComponent} from "./view-controller/skill/add-skill/add-skill.component";
import {ListSkillComponent} from "./view-controller/skill/list-skill/list-skill.component";
import {ShowTeamComponent} from "./view-controller/team/show-team/show-team.component";
import { ShowUserComponent } from './view-controller/user/show-user/show-user.component';
import { UpdateUserComponent } from './view-controller/user/update-user/update-user.component';
import { LoginComponent } from './view-controller/login/login.component';
import { UpdateDepartComponent } from './view-controller/department/update-depart/update-depart.component';
import { ShowReportComponent } from './view-controller/team/show-report/show-report.component';
import {AuthService} from "./service/auth.service";
import {AuthInterceptor} from "./helper/auth.interceptor";


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AddUserComponent,
    AddDepartComponent,
    AddTeamComponent,
    AddSkillComponent,
    ListUserComponent,
    ListDepartComponent,
    ListTeamComponent,
    ListSkillComponent,
    ShowTeamComponent,
    ShowUserComponent,
    UpdateUserComponent,
    LoginComponent,
    UpdateDepartComponent,
    ShowReportComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
