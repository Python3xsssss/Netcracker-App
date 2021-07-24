import {Component, OnInit} from '@angular/core';

import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user.model";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SkillService} from "../../../service/skill.service";
import {Skill} from "../../../model/skill.model";
import {SkillLevel} from "../../../model/skillLevel.model";

@Component({
  selector: 'app-show-user',
  templateUrl: './show-user.component.html',
  styleUrls: ['./show-user.component.css']
})
export class ShowUserComponent implements OnInit {
  id!: number;
  user!: User;
  skills: Skill[] = [];
  addForm!: FormGroup;
  show: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private skillService: SkillService,
  ) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.userService.getUserById(this.id)
      .subscribe(data => {
        this.user = data.result;
      }, error => console.log(error));
  }

  updateUser(id: number): void {
    this.router.navigate(['update-user', id]);
  };

  deleteUser(): void {
    this.userService.deleteUser(this.id).subscribe(data => {
      this.router.navigate(['users']);
    }, error => console.log(error));

  };

  addSkillLevel(): void {
    this.show = true;
    this.addForm = this.formBuilder.group({
      id: [],
      skill: [null, Validators.required],
      level: [0, Validators.required]
    });

    this.skillService.getSkills()
      .subscribe(data => {
        this.skills = data.result;
      }, error => console.log(error));
  }

  deleteSkillLevel(idToDelete: number): void {
    this.user.skillLevels = this.user.skillLevels.filter(sl => sl.id !== idToDelete);
    console.log(this.user);
    this.userService.deleteSkillLevel(this.user.id, idToDelete)
      .subscribe(data => {
        this.userService.getUserById(this.id)
          .subscribe(data => {
            this.user = data.result;
          }, error => console.log(error));
      }, error => console.log(error));
  }

  onSubmit() {
    let value = this.addForm.value;

    for (let skill of this.skills) {
      if (skill.id === Number(value.skill)) {
        value.skill = skill;
        break;
      }
    }

    let skillLevel: SkillLevel = value;

    console.log(this.user);
    this.userService.createSkillLevel(skillLevel, this.user.id)
      .subscribe(data => {
        console.log(data.message);
        this.userService.getUserById(this.id)
          .subscribe(data => {
            this.user = data.result;
          }, error => console.log(error));
        this.show = false;
      }, error => console.log(error));
  }

}
