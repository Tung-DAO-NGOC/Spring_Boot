package tung.daongoc.employee;


import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import lombok.extern.slf4j.Slf4j;
import tung.daongoc.employee.model.Employee;
import tung.daongoc.employee.repository.EmployeeRepository;

@DataJpaTest
@Sql({"/employee.sql"})
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void getEmployeeFullName() {
        Employee employee = employeeRepository.getById(1);
        Assertions.assertThat(employee.getFullName()).isEqualTo("Melonie Bilham");
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void getEmployeeAge() {
        Employee employee = employeeRepository.getById(1);
        Assertions.assertThat(employee.getAge()).isEqualTo(36);
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void getEmployeeID() {
        Employee employee = employeeRepository.getById(5);
        Assertions.assertThat(employee.getId()).isEqualTo(5);
    }

    @Test
    @Order(4)
    public void getFirstNameAndLastName() {
        List<Employee> employee =
                employeeRepository.findByFirstNameAndLastName("Ninette", "Bockmaster");
        log.info("The employee list are:");
        log.info(employee.toString());
        Assertions.assertThat(employee.size()).isEqualTo(1);
    }

    @Test
    @Order(5)
    public void findEmailAffix() {
        List<Employee> empList = employeeRepository.findByEmailContaining("jp");
        Assertions.assertThat(empList.size()).isEqualTo(8);
    }

    @Test
    @Order(6)
    public void salaryBetween() {
        List<Employee> empList = employeeRepository.findBySalaryBetween(2400L, 2500L);
        Assertions.assertThat(empList.size()).isEqualTo(9);
    }



}
