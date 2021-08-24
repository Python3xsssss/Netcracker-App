package com.netcracker.skillstable.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillLevel {
    @Getter private static final Integer entTypeId = 7;
    private Integer id;

    @NotEmpty(message = "Level should not be empty")
    @Min(value = 0, message = "Skill level should be >= 0")
    @Max(value = 5, message = "Skill level should be <= 5")
    private Integer level;
    @Getter private static final Integer levelId = 21;

    @NotNull(message = "Skill level should refer to existing skill")
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
