import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";

import {AppComponent} from './app.component';
import {HomeComponent} from './view-controller/home/home.component';
import {AddUserComponent} from './view-controller/user/add-user/add-user.component';
import { ListUserComponent } from './view-controller/user/list-user/list-user.component';
import { AddDepartComponent } from './view-controller/department/add-depart/add-depart.component';
import { ListDepartComponent } from './view-controller/department/list-depart/list-depart.component';
import {AddTeamComponent} from "./view-controller/team/add-team/add-team.component";
import {ListTeamComponent} from "./view-controller/team/list-team/list-team.component";


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AddUserComponent,
    AddDepartComponent,
    AddTeamComponent,
    ListUserComponent,
    ListDepartComponent,
    ListTeamComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
