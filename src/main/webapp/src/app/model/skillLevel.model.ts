import {Skill} from "./skill.model";

export class SkillLevel {
  id: number;
  skill: Skill;
  level: number;


  constructor(id: number, skill: Skill, level: number) {
    this.id = id;
    this.skill = skill;
    this.level = level;
  }
}
