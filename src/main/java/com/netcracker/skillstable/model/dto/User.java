package com.netcracker.skillstable.model.dto;

import com.netcracker.skillstable.model.dto.attr.Position;
import com.netcracker.skillstable.model.dto.attr.Role;

import java.util.HashSet;
import java.util.Set;

public class User {
    // General
    private Long id;
    private static final Long entTypeId = 1L;
    private String username;

    private String password;

    private Set<Role> roles = new HashSet<>();
    private static final Long roleId = 14L;

    // Personal info
    private String firstName, lastName;
    private static final Long firstNameId = 1L, lastNameId = 2L;

    private Integer age;
    private static final Long ageId = 24L;

    private String email;
    private static final Long emailId = 16L;

    private String about;
    private static final Long aboutId = 25L;

    // Work info
    private Department department;
    private static final Long departmentRefId = 18L;

    private Team team;
    private static final Long teamRefId = 4L;

    private Position position;
    private static final Long positionId = 26L;

    private Set<Skill> skills = new HashSet<>();
    private static final Long skillRefId = 5L;


    public User() {
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
            Long id,
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
        this.skills = skills;
    }

    public static Long getEntTypeId() {
        return entTypeId;
    }

    public static Long getRoleId() {
        return roleId;
    }

    public static Long getFirstNameId() {
        return firstNameId;
    }

    public static Long getLastNameId() {
        return lastNameId;
    }

    public static Long getAgeId() {
        return ageId;
    }

    public static Long getEmailId() {
        return emailId;
    }

    public static Long getAboutId() {
        return aboutId;
    }

    public Department getDepartment() {
        return department;
    }

    public static Long getDepartmentRefId() {
        return departmentRefId;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public static Long getTeamRefId() {
        return teamRefId;
    }

    public static Long getPositionId() {
        return positionId;
    }

    public static Long getSkillRefId() {
        return skillRefId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Department getDepartmentId() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeamId(Team team) {
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
