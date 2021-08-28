import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {TeamService} from "../../../service/team.service";
import {DepartmentService} from "../../../service/department.service";
import {Department} from "../../../model/department.model";
import {User} from "../../../model/user.model";
import {Team} from "../../../model/team.model";
import {UserService} from "../../../service/user.service";

@Component({
  selector: 'app-form-team',
  templateUrl: './form-team.component.html',
  styleUrls: ['./form-team.component.scss']
})
export class FormTeamComponent implements OnInit {
  teamId!: number | null;
  team!: Team;
  teamForm!: FormGroup;
  departments: Department[] = [];
  users: User[] = [];
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private teamService: TeamService,
    private departService: DepartmentService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.teamId = this.route.snapshot.params['id'];

    if (this.teamId !== undefined && this.teamId !== null) {
      this.teamService.getTeamById(this.teamId).subscribe((team) => {
        this.team = team;
        let leaderSelectedId: number | null =
          (this.team.leader !== null && this.team.leader.id !== null) ? this.team.leader.id : null;
        let departSelectedId: number | null =
          (this.team.superior !== null && this.team.superior.id !== null) ? this.team.superior.id : null;

        this.teamForm = this.formBuilder.group({
          id: [this.team.id],
          name: [this.team.name, Validators.required],
          about: [this.team.about],
          superior: [departSelectedId, Validators.required],
          leader: [leaderSelectedId, Validators.required]
        });
      });
    } else {
      this.teamForm = this.formBuilder.group({
        id: [],
        name: ['', Validators.required],
        about: [''],
        superior: [null, Validators.required],
        leader: [null, Validators.required]
      });
    }


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
    return this.teamForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.teamForm.invalid) {
      console.log("Form is invalid!");
      return;
    }

    let value = this.teamForm.value;
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

    this.team = value;
    if (this.teamId !== undefined) {
      this.teamService.updateTeam(this.team)
        .subscribe((team) => {
          this.team = team;
          this.router.navigate(['team', this.teamId]);
        });
    } else {
      this.teamService.createTeam(this.team)
        .subscribe((team) => {
          this.teamId = team.id;
          this.team = team;
          this.router.navigate(['team', this.teamId]);
        });
    }
  }

  onCancel() {
    if (this.teamId !== undefined) {
      this.router.navigate(['team', this.teamId]);
    } else {
      this.router.navigate(['home']);
    }

  }
}
