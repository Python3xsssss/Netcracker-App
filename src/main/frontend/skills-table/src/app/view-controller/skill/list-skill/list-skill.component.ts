import {Component, OnInit} from '@angular/core';

import {Router} from "@angular/router";
import {SkillService} from "../../../service/skill.service";
import {Skill} from "../../../model/skill.model";

@Component({
  selector: 'app-list-skill',
  templateUrl: './list-skill.component.html',
  styleUrls: ['./list-skill.component.scss']
})
export class ListSkillComponent implements OnInit {

  constructor(private router: Router, private skillService: SkillService) {
  }

  skills: Skill[] = [];

  ngOnInit() {
    this.skillService.getSkills()
      .subscribe((skills) => {
        this.skills = skills;
      });
  }

  deleteSkill(skill: Skill): void {
    if (skill.id !== null) {
      this.skillService.deleteSkill(skill.id)
        .subscribe(() => {
          this.skills = this.skills.filter(s => s !== skill);
        })
    }
  };

  addSkill(): void {
    this.router.navigate(['add-skill']);
  };
}
