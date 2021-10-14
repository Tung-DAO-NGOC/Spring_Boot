package tung.daongoc.employeemanagement.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    @CsvIgnore
    private int id;
    @CsvBindByName(column = "firstName")
    private String firstName;
    @CsvBindByName(column = "lastName")
    private String lastName;
    @CsvIgnore
    private String fullName;
    @CsvBindByName(column = "emailID")
    private String emailID;
    @CsvBindByName(column = "passportNumber")
    private String passportNumber;

    public void provideFullName() {
        this.fullName = String.join(" ", this.firstName, this.lastName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            Employee empF = (Employee) obj;
            return empF.id == this.id ? true : false;
        } else {
            return false;
        }
    }

    public boolean containsIgnoreCase(String src, String what) {
        what = what.toLowerCase();
        src = src.toLowerCase();
        return src.contains(what);
    }

    public boolean matchingName(String keyword) {
        return this.containsIgnoreCase(this.fullName, keyword);
    }

    public boolean matchingEmail(String keyword) {
        return this.containsIgnoreCase(this.emailID, keyword);
    }
}
