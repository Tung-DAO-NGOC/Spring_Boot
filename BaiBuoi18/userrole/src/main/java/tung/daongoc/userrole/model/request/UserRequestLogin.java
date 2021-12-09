package tung.daongoc.userrole.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRequestLogin {
    @NotNull(message = "Please enter your email")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;
    @NotNull(message = "Please enter your password")
    private String password;
}
