package com.netcracker.skillstable.model.dto;

import com.netcracker.skillstable.model.dto.attr.Position;
import com.netcracker.skillstable.model.dto.attr.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.HashSet;
import java.util.Objects;
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

    public User toUserNoRefs() {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .roles(this.roles)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .age(this.age)
                .about(this.about)
                .position(this.position)
                .build();
    }

    public void deleteSkillLevel(Skill skill) {
        skillLevels.removeIf(level -> skill.getId().equals(level.getSkill().getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(age, user.age) && Objects.equals(email, user.email) && Objects.equals(about, user.about) && Objects.equals(department, user.department) && Objects.equals(team, user.team) && position == user.position && Objects.equals(skillLevels, user.skillLevels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles, firstName, lastName, age, email, about, department, team, position, skillLevels);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", about='" + about + '\'' +
                ", department=" + ((department != null) ? department.name : "None") +
                ", team=" + ((team != null) ? team.name : "None") +
                ", position=" + position +
                ", skillLevels=" + skillLevels +
                '}';
    }
}
