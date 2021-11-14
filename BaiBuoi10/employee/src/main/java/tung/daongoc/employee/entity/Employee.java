package tung.daongoc.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with", toBuilder = true)
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passportNumber;
    private byte[] avatar;
}
