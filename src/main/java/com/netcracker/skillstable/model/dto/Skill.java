package com.netcracker.skillstable.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Getter private static final Integer entTypeId = 2;
    private Integer id;
    private String name;

    private String about;
    @Getter private static final Integer aboutId = 25;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id.equals(skill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
