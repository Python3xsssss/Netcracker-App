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
      }, error => console.log(error));

    this.teamService.getTeams()
      .subscribe(data => {
        this.teams = data.result;
      }, error => console.log(error));

  }

  onDepartSelect(departId: any) {
    this.teamsInDepart = [];
    for (let team of this.teams) {
      if (team.superior.id === Number(departId)) {
        this.teamsInDepart.push(team);
      }
    }
  }

  onSubmit() {
    let value = this.addForm.value;

    for (let depart of this.departs) {
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
    console.log(user);
    this.userService.createUser(user)
      .subscribe(data => {
        this.router.navigate(['home']);
      }, error => console.log(error));
  }

}
