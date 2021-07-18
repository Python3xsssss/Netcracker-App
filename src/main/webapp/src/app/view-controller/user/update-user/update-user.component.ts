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
  depSelected!: number;
  teamSelected!: number;

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
      this.depSelected = this.user.department.id;
      this.teamSelected = this.user.team.id;

      this.addForm = this.formBuilder.group({
        id: [],
        username: [this.user.username, Validators.required],
        password: [this.user.password, Validators.required],
        //role: [null, Validators.required],
        firstName: [this.user.firstName, Validators.required],
        lastName: [this.user.lastName, Validators.required],
        age: [this.user.age, Validators.required],
        email: [this.user.email, Validators.required],
        about: [this.user.about, Validators.required],
        department: [null, Validators.required],
        team: [null, Validators.required]
      });
    }, error => console.log(error));

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
    user.id = this.id;
    console.log(user);
    this.userService.updateUser(user)
      .subscribe(data => {
        this.router.navigate(['users']);
      });
  }
}
