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
      .subscribe(data => {
        this.skills = data.result;
      }, error => console.log(error));
  }

  deleteSkill(skill: Skill): void {
    if (skill.id !== null) {
      this.skillService.deleteSkill(skill.id)
        .subscribe(data => {
          this.skills = this.skills.filter(s => s !== skill);
        }, error => console.log(error))
    }
  };

  addSkill(): void {
    this.router.navigate(['add-skill']);
  };
}
