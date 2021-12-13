package tung.daongoc.userrole.model.request.user;

import javax.validation.constraints.Size;

public class UserRequestUpdatePassword {
    @Size(min = 6, message = "Your password must be at least 6 characters")
    private String password;
}
