import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {SkillService} from "../../../service/skill.service";
import {Skill} from "../../../model/skill.model";

@Component({
  selector: 'app-add-skill',
  templateUrl: './form-skill.component.html',
  styleUrls: ['./form-skill.component.scss']
})
export class FormSkillComponent implements OnInit {
  skillId!: number | null;
  skill!: Skill;
  skillForm!: FormGroup;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private skillService: SkillService
  ) {
  }

  ngOnInit(): void {
    this.skillId = this.route.snapshot.params['id'];

    if (this.skillId !== undefined && this.skillId !== null) {
      this.skillService.getSkillById(this.skillId).subscribe((skill) =>{
        this.skill = skill;
        this.skillForm = this.formBuilder.group({
          id: [this.skill.id],
          name: [this.skill.name, Validators.required],
          about: [this.skill.about],
        });
      });
    } else {
      this.skillForm = this.formBuilder.group({
        id: [],
        name: ['', Validators.required],
        about: [''],
      });
    }
  }

  get f() {
    return this.skillForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.skillForm.invalid) {
      console.log("Form is invalid!");
      return;
    }

    if (this.skillId !== undefined) {
      this.skillService.updateSkill(this.skillForm.value)
        .subscribe((skill) => {
          this.skill = skill;
          this.router.navigate(['skills']);
        });
    } else {
      this.skillService.createSkill(this.skillForm.value)
        .subscribe((skill) => {
          this.skillId = skill.id;
          this.skill = skill;
          this.router.navigate(['skills']);
        });
    }
  }

  onCancel() {
    this.router.navigate(['skills']);
  }
}
