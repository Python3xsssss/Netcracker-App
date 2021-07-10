import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AddUserComponent} from './user/add-user/add-user.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HomeComponent} from './home/home.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AddUserComponent,
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
