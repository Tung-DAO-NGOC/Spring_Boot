package tung.daongoc.userrole.constant;

import java.util.stream.Stream;

public enum Role {
    ADMIN("admin"),
    CUSTOMER("customer"),
    DEVELOPER("developer");
    private String role;

    private Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static Role of(String role){
        return Stream.of(Role.values())
                .filter(p -> p.getRole().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
