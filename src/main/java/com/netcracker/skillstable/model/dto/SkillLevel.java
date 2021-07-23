package com.netcracker.skillstable.model.dto;

import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillLevel {
    @Getter private static final Integer entTypeId = 7;
    private Integer id;

    private Integer level;
    @Getter private static final Integer levelId = 21;

    private Skill skill;
    @Getter private static final Integer skillRefId = 5;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillLevel that = (SkillLevel) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SkillLevel{" +
                "level=" + level +
                ", skill=" + skill.getName() +
                '}';
    }
}
