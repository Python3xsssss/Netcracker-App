package com.netcracker.skillstable.model.dto;

public class Skill {
    private static final Integer entTypeId = 2;
    private Integer id;
    private String name;

    private String about;
    private static final Integer aboutId = 25;

    private Integer level;
    private static final Integer levelId = 21;


    public Skill() {
    }

    public Skill(String name, String about, Integer level) {
        this.name = name;
        this.about = about;
        this.level = level;
    }

    public Skill(Integer id, String name, String about, Integer level) {
        this(name, about, level);
        this.id = id;
    }


    public static Integer getEntTypeId() {
        return entTypeId;
    }

    public static Integer getAboutId() {
        return aboutId;
    }

    public static Integer getLevelId() {
        return levelId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
