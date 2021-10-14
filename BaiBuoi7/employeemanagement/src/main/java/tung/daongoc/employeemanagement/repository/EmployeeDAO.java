package tung.daongoc.employeemanagement.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBeanBuilder;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import tung.daongoc.employeemanagement.entity.Employee;
import tung.daongoc.employeemanagement.service.SearchRequest;

@Component
public class EmployeeDAO implements DAO<Employee> {

    private static final String PATH = "classpath:static/csv/Employee.csv";
    List<Employee> listEmp = new ArrayList<Employee>();
    Stack<Integer> idStack = new Stack<Integer>();

    private Integer providingID() {
        int result = 0;
        if (listEmp.isEmpty()) {
            result = 1;
        } else if (!idStack.isEmpty()) {
            result = idStack.pop();
        } else {
            result = listEmp.size() + 1;
        }
        return result;
    }

    EmployeeDAO() {
        try {
            File file = ResourceUtils.getFile(PATH);
            listEmp = new CsvToBeanBuilder<Employee>(new FileReader(file)).withType(Employee.class).build().parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AtomicInteger count = new AtomicInteger();

        listEmp.stream().forEach(emp -> {
            emp.provideFullName();
            emp.setId(count.incrementAndGet());
        });
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> result = listEmp;
        result.sort((emp1, emp2) -> emp1.getId() - emp2.getId());
        return result;
    }

    @Override
    public Optional<Employee> getByID(int id) {
        return listEmp.stream().filter(p -> (p.getId() == id)).findAny();
    }

    @Override
    public void add(Employee newObject) {
        newObject.provideFullName();
        newObject.setId(this.providingID());
        listEmp.add(newObject);
    }

    @Override
    public void update(Employee updatedObject) {
        updatedObject.provideFullName();
        listEmp = listEmp.stream().map(emp -> emp.getId() == updatedObject.getId() ? updatedObject : emp)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Employee deletedObject) {
        idStack.push(deletedObject.getId());
        listEmp.remove(deletedObject);
    }

    @Override
    public List<Employee> sort(SearchRequest searchRequest) {
        List<Employee> result = listEmp;
        switch (searchRequest.getField()) {
            case "email":
                result = result.stream().filter(emp -> emp.matchingEmail(searchRequest.getKeyword()))
                        .collect(Collectors.toList());
                switch (searchRequest.getOrder()) {
                    case "inc":
                        result.sort((emp1, emp2) -> emp1.getEmailID().compareToIgnoreCase(emp2.getEmailID()));
                        break;
                    case "desc":
                        result.sort((emp1, emp2) -> -emp1.getEmailID().compareToIgnoreCase(emp2.getEmailID()));
                        break;
                }
                break;
            case "name":
                result = result.stream().filter(emp -> emp.matchingName(searchRequest.getKeyword()))
                        .collect(Collectors.toList());
                switch (searchRequest.getOrder()) {
                    case "inc":
                        result.sort((emp1, emp2) -> emp1.getFullName().compareToIgnoreCase(emp2.getFullName()));
                        break;
                    case "desc":
                        result.sort((emp1, emp2) -> -emp1.getFullName().compareToIgnoreCase(emp2.getFullName()));
                        break;
                }
                break;
        }
        return result;
    }

}
