package tung.daongoc.userrole.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequestCreate {

    @NotNull(message = "Please provide an email")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @NotNull(message = "Please provide your name")
    private String fullName;

    @Size(min = 6, message = "Your password must be at least 6 characters")
    private String password;

}
