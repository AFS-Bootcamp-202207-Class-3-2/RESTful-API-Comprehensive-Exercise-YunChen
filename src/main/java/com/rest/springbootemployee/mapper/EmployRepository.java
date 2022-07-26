package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFindException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class EmployRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployRepository() {
        employees.add(new Employee("5", "Lily", 20, "Female", 8000));
        employees.add(new Employee("1", "Mike", 20, "Female", 6000));
        employees.add(new Employee("3", "Lucy", 20, "Female", 8000));
        employees.add(new Employee("4", "czy", 20, "Man", 18000));
        employees.add(new Employee("2", "John", 20, "Female", 4000));
    }


    public List<Employee> queryByGender(String gender) {
        return employees.stream().
                filter((employee) -> employee.getGender().equals(gender)).
                collect(Collectors.toList());
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public Employee queryEmployeeById(String id) throws EmployeeNotFindException {
        return employees.stream().
                filter((employee -> employee.getId().equals(id)))
                .findFirst()
                .orElseThrow(EmployeeNotFindException::new);
    }


    public List<Employee> findByPage(int page, int pageSize) {
        return employees.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

}
