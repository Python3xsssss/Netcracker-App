import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";
import {Department} from "../../../model/department.model";
import {Team} from "../../../model/team.model";
import {Role} from "../../../model/role.model";
import {DepartmentService} from "../../../service/department.service";
import {TeamService} from "../../../service/team.service";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {
  id!: number;
  user!: User;
  addForm!: FormGroup;
  departs: Department[] = [];
  teams: Team[] = [];
  teamsInDepart: Team[] = [];
  roles = Object.keys(Role).filter(key => isNaN(Number(key)));
  depSelectedId!: number;
  teamSelectedId!: number;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private departService: DepartmentService,
    private teamService: TeamService
  ) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.userService.getUserById(this.id).subscribe(data => {
      this.user = data.result;
      this.depSelectedId = this.user.department.id;
      this.teamSelectedId = this.user.team.id;

      this.departService.getDepartments()
        .subscribe(data => {
          this.departs = data.result;
        }, error => console.log(error));

      this.teamService.getTeams()
        .subscribe(data => {
          this.teams = data.result;
          this.onDepartSelect(this.user.department.id);
        }, error => console.log(error));

      this.addForm = this.formBuilder.group({
        id: [this.user.id],
        username: [this.user.username, Validators.required],
        password: [this.user.password, Validators.required],
        role: [this.user.roles[0], Validators.required],
        firstName: [this.user.firstName, Validators.required],
        lastName: [this.user.lastName, Validators.required],
        age: [this.user.age, Validators.required],
        email: [this.user.email, Validators.required],
        about: [this.user.about, Validators.required],
        department: [this.user.department.id, Validators.required],
        team: [this.user.team.id, Validators.required],
        skillLevels: [this.user.skillLevels]
      });
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

    Object.assign(this.user, value);
    this.user.roles = [];
    this.user.roles.push(value.role);
    console.log(this.user);
    this.userService.updateUser(this.user)
      .subscribe(data => {
        this.router.navigate(['user', this.id]);
      }, error => console.log(error));
  }
}
