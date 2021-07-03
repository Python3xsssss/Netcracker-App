package com.netcracker.skillstable.model.dto;

public class Skill {
    private static final Long entTypeId = 2L;
    private Long id;
    private String name;

    private String about;
    private static final Long aboutId = 25L;

    private Integer level;
    private static final Long levelId = 21L;


    public Skill() {
    }

    public Skill(String name, String about, Integer level) {
        this.name = name;
        this.about = about;
        this.level = level;
    }


    public static Long getEntTypeId() {
        return entTypeId;
    }

    public static Long getAboutId() {
        return aboutId;
    }

    public static Long getLevelId() {
        return levelId;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
