package com.netcracker.skillstable.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skill {
    @Getter private static final Integer entTypeId = 2;
    private Integer id;
    private String name;

    private String about;
    @Getter private static final Integer aboutId = 25;


    public Skill() {
    }

    @Builder
    public Skill(Integer id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }
}
