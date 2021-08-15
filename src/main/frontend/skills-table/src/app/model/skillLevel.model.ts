import {Skill} from "./skill.model";

export class SkillLevel {
  id: number | null;
  skill: Skill;
  level: number;


  constructor(id: number | null, skill: Skill, level: number) {
    this.id = id;
    this.skill = skill;
    this.level = level;
  }

  public equals(obj: SkillLevel): boolean {
    if (this == obj) return true;
    if (obj == null) return false;
    return this.id == obj.id;
  }
}
