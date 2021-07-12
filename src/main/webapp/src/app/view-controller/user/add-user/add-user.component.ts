import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators, FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {Department} from "../../../model/department.model";
import {Team} from "../../../model/team.model";
import {DepartmentService} from "../../../service/department.service";
import {TeamService} from "../../../service/team.service";
import {Role} from "../../../model/role.model";
import { KeyValue } from '@angular/common';
import {User} from "../../../model/user.model";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  addForm!: FormGroup;
  departs: Department[] = [];
  teams: Team[] = [];
  teamsInDepart: Team[] = [];
  roles = Object.keys(Role).filter(key => isNaN(Number(key)));

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private departService: DepartmentService,
    private teamService: TeamService
  ) {
  }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      id: [],
      username: ['', Validators.required],
      password: ['', Validators.required],
      //role: [null, Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      age: [0, Validators.required],
      email: ['', Validators.required],
      about: ['', Validators.required],
      department: [null, Validators.required],
      team: [null, Validators.required]
    });

    this.departService.getDepartments()
      .subscribe(data => {
        this.departs = data.result;
      });
    this.teamService.getTeams()
      .subscribe(data => {
        this.teams = data.result;
      });

  }

  onDepartSelect(departId: any) {
    this.teamsInDepart = [];
    for (let i = 0; i < this.teams.length; i++) {
      if (this.teams[i].superior.id === Number(departId)) {
        this.teamsInDepart.push(this.teams[i]);
      }
    }
  }

  onSubmit() {
    let value = this.addForm.value;

    for (let i = 0; i < this.departs.length; i++) {
      if (this.departs[i].id === Number(value.department)) {
        value.department = this.departs[i];
      }
    }

    for (let i = 0; i < this.teams.length; i++) {
      if (this.teams[i].id === Number(value.team)) {
        value.team = this.teams[i];
      }
    }

    let user: User = value;
    console.log(user);
    this.userService.createUser(user)
      .subscribe(data => {
        this.router.navigate(['home']);
      });
  }

}
