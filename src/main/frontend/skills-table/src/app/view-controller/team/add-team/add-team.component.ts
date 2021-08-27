import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {TeamService} from "../../../service/team.service";
import {DepartmentService} from "../../../service/department.service";
import {Department} from "../../../model/department.model";
import {User} from "../../../model/user.model";
import {Team} from "../../../model/team.model";
import {UserService} from "../../../service/user.service";

@Component({
  selector: 'app-add-team',
  templateUrl: './add-team.component.html',
  styleUrls: ['./add-team.component.scss']
})
export class AddTeamComponent implements OnInit {
  addForm!: FormGroup;
  departments: Department[] = [];
  submitted = false;
  users: User[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private teamService: TeamService,
    private departService: DepartmentService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      about: [''],
      superior: [null, Validators.required],
      leader: [null, Validators.required]
    });

    this.departService.getDepartments()
      .subscribe((departments) => {
        this.departments = departments;
      });

    this.userService.getUsers()
      .subscribe((users) => {
        this.users = users;
      });
  }

  get f() {
    return this.addForm.controls;
  }

  onSuperiorSelect(departId: any) {
  }

  onSubmit() {
    this.submitted = true;
    if (this.addForm.invalid) {
      console.log("Form is invalid!");
      return;
    }

    let value = this.addForm.value;
    for (let depart of this.departments) {
      if (depart.id === Number(value.superior)) {
        value.superior = depart;
      }
    }

    for (let user of this.users) {
      if (user.id === Number(value.leader)) {
        value.leader = user;
        break;
      }
    }

    let team: Team = value;
    this.teamService.createTeam(team)
      .subscribe(() => {
        this.router.navigate(['teams']);
      });
  }

}
