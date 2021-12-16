package tung.daongoc.userrole.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestUpdateInfo {
    @NotNull(message = "Please provide an email")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @NotNull(message = "Please provide your name")
    private String fullName;

    private String uuid;
}
