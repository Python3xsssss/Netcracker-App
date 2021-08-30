import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {DepartmentService} from "../../../service/department.service";
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";
import {Department} from "../../../model/department.model";

@Component({
  selector: 'app-form-depart',
  templateUrl: './form-depart.component.html',
  styleUrls: ['./form-depart.component.scss']
})
export class FormDepartComponent implements OnInit {
  departId!: number | null;
  department!: Department;
  departForm!: FormGroup;
  users: User[] = [];
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private departmentService: DepartmentService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.departId = this.route.snapshot.params['id'];

    if (this.departId !== undefined && this.departId !== null) {
      this.departmentService.getDepartmentById(this.departId).subscribe((department) => {
        this.department = department;
        let leaderSelectedId: number | null;
        if (this.department.leader !== null && this.department.leader.id !== null) {
          leaderSelectedId = this.department.leader.id;
        } else {
          leaderSelectedId = null;
        }

        this.departForm = this.formBuilder.group({
          id: [this.department.id],
          name: [this.department.name, Validators.required],
          about: [this.department.about],
          leader: [leaderSelectedId, Validators.required]
        });
      });
    } else {
      this.departForm = this.formBuilder.group({
        id: [],
        name: ['', Validators.required],
        about: [''],
        leader: [null, Validators.required]
      });
    }

    this.userService.getUsers()
      .subscribe((users) => {
        this.users = users;
      });
  }

  get f() {
    return this.departForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.departForm.invalid) {
      console.log("Form is invalid!");
      return;
    }

    let value = this.departForm.value;

    for (let user of this.users) {
      if (user.id === Number(value.leader)) {
        value.leader = user;
        break;
      }
    }

    this.department = value;
    if (this.departId !== undefined) {
      this.departmentService.updateDepartment(this.department)
        .subscribe((department) => {
          this.department = department;
          this.router.navigate(['home']);
        });
    } else {
      this.departmentService.createDepartment(this.department)
        .subscribe((department) => {
          this.departId = department.id;
          this.department = department;
          this.router.navigate(['home']);
        });
    }

  }

  onCancel() {
    this.router.navigate(['home']);
  }

}
