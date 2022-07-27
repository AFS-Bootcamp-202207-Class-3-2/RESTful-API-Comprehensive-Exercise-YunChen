package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @BeforeEach
    void initData(){
        employeeRepository.setNextId("1");
        employeeRepository.getEmployees().clear();
    }
    @Test
    void should_return_all_employees_when_find_all_given_given_employees() {
        List<Employee> prepareEmployees = new ArrayList<>();
        //given
        Employee firstEmployee = new Employee("1","Susan",23,"Female",9000,"");
        Employee secondEmployee = new Employee("1", "Mathew", 23, "Female", 12000, "");
        prepareEmployees.add(firstEmployee);
        prepareEmployees.add(secondEmployee);
//        employeeRepository.insert(firstEmployee);
//        employeeRepository.insert(secondEmployee);
        given(employeeService.findALl()).willReturn(prepareEmployees);
        //when
        //should
        assertEquals(2, prepareEmployees.size());
    }
}