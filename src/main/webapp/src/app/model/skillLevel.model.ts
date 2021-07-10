import {Skill} from "./skill.model";

export class SkillLevel {
  id: number;
  name: string;
  level: number;
  skill: Skill;


  constructor(id: number, name: string, level: number, skill: Skill) {
    this.id = id;
    this.name = name;
    this.level = level;
    this.skill = skill;
  }
}
