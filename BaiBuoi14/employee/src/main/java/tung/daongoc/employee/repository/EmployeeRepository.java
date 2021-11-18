package tung.daongoc.employee.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tung.daongoc.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> findByEmailContaining(String affix);

    List<Employee> findBySalaryBetween(Long startSalary, Long endSalary);
}
