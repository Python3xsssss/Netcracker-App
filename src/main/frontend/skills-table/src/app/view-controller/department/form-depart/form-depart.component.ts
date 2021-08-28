import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
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
  addForm!: FormGroup;
  users: User[] = [];
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private departmentService: DepartmentService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      about: [''],
      leader: [null, Validators.required]
    });

    this.userService.getUsers()
      .subscribe((users) => {
        this.users = users;
      });
  }

  get f() {
    return this.addForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.addForm.invalid) {
      console.log("Form is invalid!");
      return;
    }

    let value = this.addForm.value;

    for (let user of this.users) {
      if (user.id === Number(value.leader)) {
        value.leader = user;
        break;
      }
    }

    let department: Department = value;
    this.departmentService.createDepartment(department)
      .subscribe(() => {
        this.router.navigate(['home']);
      });
  }

}
