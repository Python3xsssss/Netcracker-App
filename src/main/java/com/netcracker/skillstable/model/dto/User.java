package com.netcracker.skillstable.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netcracker.skillstable.model.dto.enumeration.Position;
import com.netcracker.skillstable.model.dto.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"allAuthorities"})
public class User implements UserDetails {
    // General
    private Integer id;
    @Getter
    private static final Integer entTypeId = 1;
    private String username;

    private String password;
    @Getter
    private static final Integer passwordId = 10;

    private Set<Role> roles = new HashSet<>();
    @Getter
    private static final Integer roleId = 14;

    @JsonIgnore
    private Set<? extends GrantedAuthority> authorities = new HashSet<>();
    @Getter
    private static final Integer authId = 7;

    // Personal info
    private String firstName, lastName;
    @Getter
    private static final Integer firstNameId = 1, lastNameId = 2;

    private String email;
    @Getter
    private static final Integer emailId = 16;

    private String about;
    @Getter
    private static final Integer aboutId = 25;

    // Work info
    private Department department;
    @Getter
    private static final Integer departmentRefId = 18;

    private Team team;
    @Getter
    private static final Integer teamRefId = 4;

    private Position position;
    @Getter
    private static final Integer positionId = 26;

    private Set<SkillLevel> skillLevels = new HashSet<>();
    @Getter
    private static final Integer skillLevelRefId = 6;

    //Other
    private boolean isActive;
    @Getter
    private static final Integer isActiveId = 8;

    private boolean isNonLocked;
    @Getter
    private static final Integer isNonLockedId = 9;


    // Constructor for User w/o OrgItem references
    public User(
            Integer id,
            String username,
            String password,
            Set<Role> roles,
            Set<? extends GrantedAuthority> authorities,
            String firstName,
            String lastName,
            String email,
            String about,
            Position position,
            Set<SkillLevel> skillLevels,
            boolean isActive,
            boolean isNonLocked
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.about = about;
        this.position = position;
        this.skillLevels = skillLevels;
        this.isActive = isActive;
        this.isNonLocked = isNonLocked;
    }


    public User toUserNoRefs() {
        return new User(
                this.id,
                this.username,
                this.password,
                this.roles,
                this.authorities,
                this.firstName,
                this.lastName,
                this.email,
                this.about,
                this.position,
                this.skillLevels,
                this.isActive,
                this.isNonLocked
        );
    }

    public void addSkillLevel(SkillLevel skillLevel) {
        skillLevels.add(skillLevel);
    }

    public void deleteSkillLevel(Integer skillLevelId) {
        skillLevels.removeIf(level -> skillLevelId.equals(level.getId()));
    }

    public void deleteSkillLevel(Skill skill) {
        skillLevels.removeIf(level -> skill.getId().equals(level.getSkill().getId()));
    }

    public void addRole(Role role) {
        this.roles.add(role);
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; //isActive;
    }
}
