package com.netcracker.skillstable.model.dto;

import com.netcracker.skillstable.model.dto.attr.Position;
import com.netcracker.skillstable.model.dto.attr.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class User {
    // General
    private Integer id;
    @Getter private static final Integer entTypeId = 1;
    private String username;

    private String password;

    private Set<Role> roles = new HashSet<>();
    @Getter private static final Integer roleId = 14;

    // Personal info
    private String firstName, lastName;
    @Getter private static final Integer firstNameId = 1, lastNameId = 2;

    private Integer age;
    @Getter private static final Integer ageId = 24;

    private String email;
    @Getter private static final Integer emailId = 16;

    private String about;
    @Getter private static final Integer aboutId = 25;

    // Work info
    private Department department;
    @Getter private static final Integer departmentRefId = 18;

    private Team team;
    @Getter private static final Integer teamRefId = 4;

    private Position position;
    @Getter private static final Integer positionId = 26;

    private Set<SkillLevel> skillLevels = new HashSet<>();
    @Getter private static final Integer skillLevelRefId = 6;


    public User() {
    }

    @Builder
    public User(
            Integer id,
            String username,
            String password,
            @Singular Set<Role> roles,
            String firstName,
            String lastName,
            Integer age,
            String email,
            String about,
            Department department,
            Team team,
            Position position,
            @Singular Set<SkillLevel> skillLevels
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.about = about;
        this.department = department;
        this.team = team;
        this.position = position;
        this.skillLevels = skillLevels;
    }
}
