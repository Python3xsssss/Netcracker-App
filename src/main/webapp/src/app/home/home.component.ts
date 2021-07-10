import { Component, OnInit } from '@angular/core';

import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, private apiService: UserService) {
  }

  users: User[] = [];

  ngOnInit() {
    this.apiService.getUsers()
      .subscribe(data => {
        this.users = data.result;
      });
  }

  deleteUser(user: User): void {
    this.apiService.deleteUser(user.id)
      .subscribe(data => {
        this.users = this.users.filter(u => u !== user);
      })
  };

  addUser(): void {
    this.router.navigate(['add-user']);
  };
}
