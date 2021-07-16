import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {TeamService} from "../../../service/team.service";
import {DepartmentService} from "../../../service/department.service";
import {Department} from "../../../model/department.model";
import {User} from "../../../model/user.model";
import {Team} from "../../../model/team.model";

@Component({
  selector: 'app-add-team',
  templateUrl: './add-team.component.html',
  styleUrls: ['./add-team.component.css']
})
export class AddTeamComponent implements OnInit {
  addForm!: FormGroup;
  departs: Department[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private teamService: TeamService,
    private departService: DepartmentService
  ) {
  }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      about: ['', Validators.required],
      superior: [null, Validators.required]
    });

    this.departService.getDepartments()
      .subscribe(data => {
        this.departs = data.result;
      }, error => console.log(error));

  }

  onSuperiorSelect(departId: any) {
  }

  onSubmit() {
    let value = this.addForm.value;
    for (let depart of this.departs) {
      if (depart.id === Number(value.superior)) {
        value.superior = depart;
      }
    }

    let team: Team = value;
    console.log(team);
    this.teamService.createTeam(team)
      .subscribe(data => {
        this.router.navigate(['home']);
      }, error => console.log(error));
  }

}
