package com.netcracker.skillstable.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillLevel {
    @Getter private static final Integer entTypeId = 7;
    private Integer id;
    private String name;

    private Integer level;
    @Getter private static final Integer levelId = 21;

    private Skill skill;
    @Getter private static final Integer skillRefId = 6;


    @Builder
    SkillLevel(Integer id, String name, Integer level, Skill skill) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.skill = skill;
    }
}
