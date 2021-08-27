package com.netcracker.skillstable.model.dto.enumeration;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.netcracker.skillstable.model.dto.enumeration.Authority.*;

@Getter
public enum Role {
    USER(Sets.newHashSet(
            USER_READ,
            DEPART_READ,
            TEAM_READ,
            SKILL_READ,
            SKILL_CREATE
    )),
    TEAMLEAD(
            Stream.concat(
                    USER.authorities.stream(),
                    Sets.newHashSet(
                            SKILL_UPDATE,
                            SKILL_DELETE
                    ).stream()
            ).collect(Collectors.toUnmodifiableSet())
    ),
    DEPARTLEAD(TEAMLEAD.authorities),
    ADMIN(Sets.newHashSet(
            Authority.values()
    )),
    CREATOR(Sets.newHashSet(
            Authority.values()
    ));

    private final Set<Authority> authorities;

    Role(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = this.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
