package com.netcracker.skillstable.model.dto;

import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Skill {
    @Getter private static final Integer entTypeId = 2;
    private Integer id;
    private String name;

    private String about;
    @Getter private static final Integer aboutId = 25;


    public Skill(Integer id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id.equals(skill.id) && Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
