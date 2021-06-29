package com.netcracker.skillstable.model.dto;

public class Skill {
    private Long id;
    private String name;
    private String description;
    private Integer level;


    public Skill() {
    }

    public Skill(String name, String description, Integer level) {
        this.name = name;
        this.description = description;
        this.level = level;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
