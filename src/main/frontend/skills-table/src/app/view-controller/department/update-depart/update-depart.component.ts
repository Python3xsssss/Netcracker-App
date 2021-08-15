import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {DepartmentService} from "../../../service/department.service";
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";
import {Department} from "../../../model/department.model";

@Component({
  selector: 'app-update-depart',
  templateUrl: './update-depart.component.html',
  styleUrls: ['./update-depart.component.scss']
})
export class UpdateDepartComponent implements OnInit {
  addForm!: FormGroup;
  id!: number;
  department!: Department;
  users: User[] = [];
  leaderSelectedId!: number;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private departmentService: DepartmentService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.departmentService.getDepartmentById(this.id).subscribe(data => {
      this.department = data.result;
      if (this.department.leader !== null && this.department.leader.id !== null) {
        this.leaderSelectedId = this.department.leader.id;

        this.addForm = this.formBuilder.group({
          id: [this.department.id],
          name: [this.department.name, Validators.required],
          about: [this.department.about, Validators.required],
          leader: [this.department.leader.id, Validators.required]
        });

        this.userService.getUsers()
          .subscribe(data => {
            this.users = data.result;
          });
      }
    }, error => console.log(error));
  }

  onSubmit() {
    let value = this.addForm.value;

    for (let user of this.users) {
      if (user.id === Number(value.leader)) {
        value.leader = user;
        break;
      }
    }

    Object.assign(this.department, value);
    this.departmentService.updateDepartment(this.department)
      .subscribe(data => {
        this.router.navigate(['home']);
      }, error => console.log(error));
  }
}
