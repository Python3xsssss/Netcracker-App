import {Component, EventEmitter, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";
import {Department} from "../../../model/department.model";
import {Team} from "../../../model/team.model";
import {Role} from "../../../model/role.model";
import {DepartmentService} from "../../../service/department.service";
import {TeamService} from "../../../service/team.service";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Position} from "../../../model/position.model";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss'],
  outputs: ['onUserUpdated']
})
export class UpdateUserComponent implements OnInit {
  id!: number;
  user!: User;
  addForm!: FormGroup;
  departs: Department[] = [];
  teams: Team[] = [];
  teamsInDepart: Team[] = [];
  positions = Object.keys(Position).filter(key => isNaN(Number(key)));
  depSelectedId!: number | null;
  teamSelectedId!: number | null;
  onUserUpdated: EventEmitter<boolean>;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private departService: DepartmentService,
    private teamService: TeamService
  ) {
    this.onUserUpdated = new EventEmitter();
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.userService.getUserById(this.id).subscribe(data => {
      this.user = data.result;
      if (this.user.department !== null) {
        this.depSelectedId = this.user.department.id;
      } else {
        this.depSelectedId = null;
      }
      if (this.user.team !== null) {
        this.teamSelectedId = this.user.team.id;
      } else {
        this.teamSelectedId = null;
      }


      this.departService.getDepartments()
        .subscribe(data => {
          this.departs = data.result;
        }, error => console.log(error));

      this.teamService.getTeams()
        .subscribe(data => {
          this.teams = data.result;
          this.onDepartSelect(this.depSelectedId);
        }, error => console.log(error));

      this.addForm = this.formBuilder.group({
        firstName: [this.user.firstName, Validators.required],
        lastName: [this.user.lastName, Validators.required],
        email: [this.user.email, Validators.required],
        about: [this.user.about, Validators.required],
        department: [this.depSelectedId, Validators.required],
        team: [this.teamSelectedId, Validators.required],
        position: [this.user.position, Validators.required],
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
    if (value.department === "null") {
      value.department = null;
      value.team = null;
    }
    if (value.team === "null") {
      value.team = null;
    }

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
    console.log(this.user);
    this.userService.updateUser(this.user)
      .subscribe(data => {
        this.onUserUpdated.emit(true);
      }, error => console.log(error));
  }

  onCancel() {
    this.onUserUpdated.emit(false);
  }
}
