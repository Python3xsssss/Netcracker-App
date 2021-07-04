package com.netcracker.skillstable.model.dto;

import com.netcracker.skillstable.model.dto.attr.Position;
import com.netcracker.skillstable.model.dto.attr.Role;

import java.util.HashSet;
import java.util.Set;

public class User {
    // General
    private Integer id;
    private static final Integer entTypeId = 1;
    private String username;

    private String password;

    private Set<Role> roles = new HashSet<>();
    private static final Integer roleId = 14;

    // Personal info
    private String firstName, lastName;
    private static final Integer firstNameId = 1, lastNameId = 2;

    private Integer age;
    private static final Integer ageId = 24;

    private String email;
    private static final Integer emailId = 16;

    private String about;
    private static final Integer aboutId = 25;

    // Work info
    private Department department;
    private static final Integer departmentRefId = 18;

    private Team team;
    private static final Integer teamRefId = 4;

    private Position position;
    private static final Integer positionId = 26;

    private Set<Skill> skills = new HashSet<>();
    private static final Integer skillRefId = 5;


    public User() {
    }

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(
            String username,
            String password,
            String firstName,
            String lastName,
            Integer age,
            String email,
            String about,
            Department department,
            Team team,
            Position position,
            Set<Skill> skills
    ) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.about = about;
        this.department = department;
        this.team = team;
        this.position = position;
        this.skills = skills;

        roles.add(Role.STAFF);
    }

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
            Department department,
            Team team,
            Position position,
            Set<Skill> skills
    ) {
        this(id, username);
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
        this.skills = skills;
    }

    public static Integer getEntTypeId() {
        return entTypeId;
    }

    public static Integer getRoleId() {
        return roleId;
    }

    public static Integer getFirstNameId() {
        return firstNameId;
    }

    public static Integer getLastNameId() {
        return lastNameId;
    }

    public static Integer getAgeId() {
        return ageId;
    }

    public static Integer getEmailId() {
        return emailId;
    }

    public static Integer getAboutId() {
        return aboutId;
    }

    public static Integer getDepartmentRefId() {
        return departmentRefId;
    }

    public static Integer getTeamRefId() {
        return teamRefId;
    }

    public static Integer getPositionId() {
        return positionId;
    }

    public static Integer getSkillRefId() {
        return skillRefId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }
}
