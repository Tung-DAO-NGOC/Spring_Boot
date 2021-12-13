package tung.daongoc.userrole.model.request.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRequestUpdateNameEmail {
    @NotNull(message = "Please provide an email")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @NotNull(message = "Please provide your name")
    private String fullName;
}
