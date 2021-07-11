import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {TeamService} from "../../../service/team.service";

@Component({
  selector: 'app-add-depart',
  templateUrl: './add-team.component.html',
  styleUrls: ['./add-team.component.css']
})
export class AddTeamComponent implements OnInit {
  addForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private teamService: TeamService) {
  }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      about: ['', Validators.required],
    });

  }

  onSubmit() {
    this.teamService.createTeam(this.addForm.value)
      .subscribe(data => {
        this.router.navigate(['home']);
      });
  }

}
