package com.netcracker.skillstable.model.dto.enumeration;

import lombok.Getter;

@Getter
public enum Authority {
    USER_READ("user:read"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    DEPART_READ("depart:read"),
    DEPART_CREATE("depart:create"),
    DEPART_UPDATE("depart:update"),
    DEPART_DELETE("depart:delete"),
    TEAM_READ("team:read"),
    TEAM_CREATE("team:create"),
    TEAM_UPDATE("team:update"),
    TEAM_DELETE("team:delete"),
    SKILL_READ("skill:read"),
    SKILL_CREATE("skill:create"),
    SKILL_UPDATE("skill:update"),
    SKILL_DELETE("skill:delete"),
    ROLE_READ("role:read"),
    ROLE_UPDATE("role:update"),
    ROLE_DELETE("role:delete");

    private final String permission;

    Authority(String permission) {
        this.permission = permission;
    }
}
