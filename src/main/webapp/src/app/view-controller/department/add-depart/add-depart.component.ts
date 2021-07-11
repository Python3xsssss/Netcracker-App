import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {DepartmentService} from "../../../service/department.service";

@Component({
  selector: 'app-add-depart',
  templateUrl: './add-depart.component.html',
  styleUrls: ['./add-depart.component.css']
})
export class AddDepartComponent implements OnInit {
  addForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private departmentService: DepartmentService) {
  }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      about: ['', Validators.required],
    });

  }

  onSubmit() {
    this.departmentService.createDepartment(this.addForm.value)
      .subscribe(data => {
        this.router.navigate(['home']);
      });
  }

}
