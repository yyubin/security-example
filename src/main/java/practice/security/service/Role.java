package practice.security.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"), MEMBER("ROLE_MEMBER");

    private final String value;
}
