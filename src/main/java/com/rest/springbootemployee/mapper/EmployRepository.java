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


}
