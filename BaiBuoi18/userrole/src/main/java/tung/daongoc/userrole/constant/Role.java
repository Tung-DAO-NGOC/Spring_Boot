package tung.daongoc.userrole.constant;

import tung.daongoc.userrole.exception.notfound.RoleNotFoundException;

import java.util.stream.Stream;

public enum Role {
    ADMIN("admin"),
    CUSTOMER("customer"),
    DEVELOPER("developer"),
    SALES("sales"),
    OPERATOR("operator"),
    TRAINER("trainer");
    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public static Role of(String roleName){
        return Stream.of(Role.values())
                .filter(p -> p.getRoleName().equalsIgnoreCase(roleName))
                .findFirst()
                .orElseThrow(RoleNotFoundException::new);
    }

}
