package tung.daongoc.userrole.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestCreate {

    @NotBlank(message = "Please provide an email")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Please provide your name")
    private String fullName;

    @Size(min = 4, message = "Your password must be at least 4 characters")
    private String password;

}
