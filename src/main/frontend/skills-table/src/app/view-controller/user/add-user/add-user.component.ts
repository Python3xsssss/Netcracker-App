import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {Department} from "../../../model/department.model";
import {Team} from "../../../model/team.model";
import {DepartmentService} from "../../../service/department.service";
import {TeamService} from "../../../service/team.service";
import {Role} from "../../../model/role.model";
import {User} from "../../../model/user.model";
import {Position} from "../../../model/position.model";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {
  addForm!: FormGroup;
  departments: Department[] = [];
  teams: Team[] = [];
  teamsInDepart: Team[] = [];
  positions = Object.keys(Position).filter(key => isNaN(Number(key)));
  submitted = false;

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
      username: ['', Validators.required],
      password: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: [''],
      about: [''],
      department: [null],
      team: [null],
      position: ['NEWCOMER', Validators.required]
    });

    this.departService.getDepartments()
      .subscribe((departments) => {
        this.departments = departments;
      });

    this.teamService.getTeams()
      .subscribe((teams) => {
        this.teams = teams;
      });
  }

  get f() {
    return this.addForm.controls;
  }

  onDepartSelect(departId: number | null) {
    this.teamsInDepart = [];
    for (let team of this.teams) {
      if (team.superior.id === Number(departId)) {
        this.teamsInDepart.push(team);
      }
    }
  }

  onSubmit() {
    this.submitted = true;
    if (this.addForm.invalid) {
      return;
    }

    let value = this.addForm.value;
    if (value.department === "null") {
      value.department = null;
      value.team = null;
    }
    if (value.team === "null") {
      value.team = null;
    }

    for (let depart of this.departments) {
      if (depart.id === Number(value.department)) {
        value.department = depart;
      }
    }

    for (let team of this.teams) {
      if (team.id === Number(value.team)) {
        value.team = team;
      }
    }

    let user: User = value;
    user.isNonLocked = true;
    user.isActive = true;
    user.roles = [];
    user.roles.push('USER');

    this.userService.createUser(user)
      .subscribe(() => {
        this.router.navigate(['users']);
      });
  }

}
