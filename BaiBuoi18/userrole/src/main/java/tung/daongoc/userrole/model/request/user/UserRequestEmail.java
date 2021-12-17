package tung.daongoc.userrole.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestEmail {
    @NotBlank(message = "Please provide an email")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;
}
