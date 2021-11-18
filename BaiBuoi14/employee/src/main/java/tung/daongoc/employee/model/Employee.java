package tung.daongoc.employee.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "employee")
@Table(name = "employee")
@Access(AccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Transient
    private String fullName;
    private String gender;
    private String address;
    private String email;
    private Long salary;

    @Column(name = "BIRTH_DAY")
    @Temporal(TemporalType.DATE)
    private Date bDay;

    @Transient
    private int age;

    public String getFullName() {
        this.fullName = String.join(" ", this.firstName, this.lastName);
        return this.fullName;
    }

    public int getAge() {
        Date birthDate = new Date(bDay.getTime());
        LocalDate birthDateLocal =
                birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthDateLocal, LocalDate.now()).getYears();
    }
}
