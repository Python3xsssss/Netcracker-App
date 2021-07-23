package com.netcracker.skillstable.model.dto;

import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
public class SkillLevel {
    @Getter private static final Integer entTypeId = 7;
    private Integer id;

    private Integer level;
    @Getter private static final Integer levelId = 21;

    private Skill skill;
    @Getter private static final Integer skillRefId = 5;


    public SkillLevel(Integer id, Integer level, Skill skill) {
        this.id = id;
        this.level = level;
        this.skill = skill;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillLevel that = (SkillLevel) o;
        return id.equals(that.id) && Objects.equals(level, that.level) && skill.equals(that.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level, skill);
    }

    @Override
    public String toString() {
        return "SkillLevel{" +
                "level=" + level +
                ", skill=" + skill.getName() +
                '}';
    }
}
