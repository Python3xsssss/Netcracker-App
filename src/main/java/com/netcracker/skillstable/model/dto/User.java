package com.netcracker.skillstable.model.dto;

import com.netcracker.skillstable.model.dto.attr.Position;
import com.netcracker.skillstable.model.dto.attr.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
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


    public User(
            Integer id,
            String username,
            String password,
            Set<Role> roles,
            String firstName,
            String lastName,
            Integer age,
            String email,
            String about,
            Position position,
            Set<SkillLevel> skillLevels
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
        this.position = position;
        this.skillLevels = skillLevels;
    }

    public User toUserNoRefs() {
        return new User(
                this.id,
                this.username,
                this.password,
                this.roles,
                this.firstName,
                this.lastName,
                this.age,
                this.email,
                this.about,
                this.position,
                this.skillLevels
        );
    }

    public void addSkillLevel(SkillLevel skillLevel) {
        System.out.println("\n\n\n00000000\n\n" + skillLevel);
        System.out.println("\n\n\n00000000\n\n" + skillLevels);
        skillLevels.add(skillLevel);
    }

    public void deleteSkillLevel(Integer skillLevelId) {
        skillLevels.removeIf(level -> skillLevelId.equals(level.getId()));
    }

    public void deleteSkillLevel(Skill skill) {
        skillLevels.removeIf(level -> skill.getId().equals(level.getSkill().getId()));
    }

    @Override
    public String toString() {
        return "User{" +
                "\nid=" + id +
                ",\nusername='" + username + '\'' +
                ",\npassword='" + password + '\'' +
                ",\nroles=" + roles +
                ",\nfirstName='" + firstName + '\'' +
                ",\nlastName='" + lastName + '\'' +
                ",\nage=" + age +
                ",\nemail='" + email + '\'' +
                ",\nabout='" + about + '\'' +
                ",\ndepartment=" + ((department != null) ? department.name : "None") +
                ",\nteam=" + ((team != null) ? team.name : "None") +
                ",\nposition=" + position +
                ",\nskillLevels=" + skillLevels +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
