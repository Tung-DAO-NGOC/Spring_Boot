package tung.daongoc.userrole.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestLogin {
    @NotBlank(message = "Please enter your email")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Please enter your password")
    @Size(min = 6, message = "Minimum length password: 6")
    private String password;

}
