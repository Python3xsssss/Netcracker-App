import { Component, OnInit } from '@angular/core';

import {Router} from "@angular/router";
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {

  constructor(private router: Router, private userService: UserService) {
  }

  users: User[] = [];

  ngOnInit() {
    this.userService.getUsers()
      .subscribe(data => {
        this.users = data.result;
      }, error => console.log(error));
  }

  deleteUser(user: User): void {
    this.userService.deleteUser(user.id)
      .subscribe(data => {
        this.users = this.users.filter(u => u !== user);
      }, error => console.log(error))
  };

  addUser(): void {
    this.router.navigate(['add-user']);
  };

  updateUser(id: number): void {
    this.router.navigate(['update-user', id]);
  };
}
