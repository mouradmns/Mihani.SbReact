package com.mihani.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mihani.security.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,

                  BRICOLEUR_READ,
                  BRICOLEUR_UPDATE,
                  BRICOLEUR_DELETE,
                  BRICOLEUR_CREATE
          )
  ),
  BRICOLEUR(
          Set.of(
                  BRICOLEUR_UPDATE,
                  BRICOLEUR_DELETE,
                  BRICOLEUR_READ,
                  BRICOLEUR_CREATE
          )
  ),
  CLIENT(
          Set.of(
                  CLIENT_READ,
                  CLIENT_UPDATE,
                  CLIENT_DELETE,
                  CLIENT_CREATE
          )
  );

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
