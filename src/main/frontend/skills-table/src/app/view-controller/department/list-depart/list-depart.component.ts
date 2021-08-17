import {Component, OnInit} from '@angular/core';

import {Router} from "@angular/router";
import {DepartmentService} from "../../../service/department.service";
import {Department} from "../../../model/department.model";
import {ErrorResponse} from "../../../model/error.response";

@Component({
  selector: 'app-list-depart',
  templateUrl: './list-depart.component.html',
  styleUrls: ['./list-depart.component.scss']
})
export class ListDepartComponent implements OnInit {

  constructor(private router: Router, private departService: DepartmentService) {
  }

  departments: Department[] = [];
  errResponse: ErrorResponse = new ErrorResponse("");

  ngOnInit() {
    this.departService.getDepartments()
      .subscribe(data => {
        this.departments = data.result;
        this.departments = this.departments.sort((dep1, dep2) => {
          if (dep1.name < dep2.name) {
            return -1;
          } else if (dep1.name > dep2.name) {
            return 1;
          } else {
            return 0;
          }
        });
      });
  }

  addDepart(): void {
    this.router.navigate(['add-depart']);
  };

  updateDepart(id: number | null): void {
    this.router.navigate(['update-depart', id]);
  }

  deleteDepart(department: Department): void {
    if (department.id !== null) {
      this.departService.deleteDepartment(department.id)
        .subscribe(data => {
          this.departments = this.departments.filter(d => d !== department);
        }, error => {
          this.errResponse = error;
          console.log(this.errResponse.message);
        })
    }
  };

  addTeam(): void {
    this.router.navigate(['add-team']);
  }

  addMember(): void {
    this.router.navigate(['add-user']);
  }

}
