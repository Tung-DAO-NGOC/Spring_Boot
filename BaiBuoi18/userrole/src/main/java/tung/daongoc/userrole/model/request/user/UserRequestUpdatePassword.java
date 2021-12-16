package tung.daongoc.userrole.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestUpdatePassword {
    @Size(min = 6, message = "Your password must be at least 6 characters")
    private String password;
    private String reconfirmPassword;
    private String uuid;
}
