import { Component, OnInit } from '@angular/core';

import {Router} from "@angular/router";
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";
import {RoleService} from "../../../service/role.service";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Position} from "../../../model/position.model";
import {Role} from "../../../model/role.model";

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.scss']
})
export class ListUserComponent implements OnInit {
  users: User[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
  ) {
  }

  ngOnInit() {
    this.userService.getUsers()
      .subscribe(data => {
        this.users = data.result;
      }, error => console.log(error));
  }

  deleteUser(user: User): void {
    if (user.id !== null) {
      this.userService.deleteUser(user.id)
        .subscribe(data => {
          this.users = this.users.filter(u => u !== user);
        }, error => console.log(error))
    }
  };

  addUser(): void {
    this.router.navigate(['add-user']);
  };

  updateUser(id: number | null): void {
    this.router.navigate(['user', id]);
  };
}
