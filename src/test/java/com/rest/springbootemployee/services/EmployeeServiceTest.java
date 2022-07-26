package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.EmployeeDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeDao employeeDao;
    @InjectMocks
    EmployeeService employeeService;
    @BeforeEach
    public void beforePrepare() {
        employeeDao.deleteAll();
    }
    @Test
    void should_return_all_employees_when_find_all_given_given_employees() {
        List<Employee> prepareEmployees = new ArrayList<>();
        //given
        Employee firstEmployee = new Employee("1", "Susan", 23, "Female", 9000, "");
        Employee secondEmployee = new Employee("1", "Mathew", 23, "Female", 12000, "");
        prepareEmployees.add(firstEmployee);
        prepareEmployees.add(secondEmployee);
        given(employeeDao.findAll()).willReturn(prepareEmployees);
        //when
        List<Employee> employees = employeeService.findALl();
        //should
        assertEquals(employees, prepareEmployees);
    }

    @Test
    void should_update_only_age_and_salary_when_update_given_employee() {
    //given
        Employee employeeToUpdate = new Employee("1", "Susan", 20, "Female", 8000, "");
        Employee employeeInUpdateRequest = new Employee("1", "Mathew", 10, "male", 21000, "");
        given (employeeDao.findById("1")).willReturn(Optional.of(employeeToUpdate));
        given (employeeDao.save(employeeToUpdate)).willReturn(employeeInUpdateRequest);
    //when
        Employee updateEmployee = employeeService.update("1", employeeToUpdate);
        //should
        assertEquals("Mathew",updateEmployee.getName());
        assertEquals(10,updateEmployee.getAge());
        assertEquals("male",updateEmployee.getGender());
        assertEquals(21000,updateEmployee.getSalary());
    }
    @Test
    void should_return_males_when_query_gender_given_param_male() {
        //given
        Employee firstEmployee = new Employee("1", "Susan", 20, "Female", 8000, "");
        Employee secondEmployee = new Employee("1", "Mathew", 10, "male", 21000, "");
        Employee thirdEmployee = new Employee("1", "John", 10, "male", 21000, "");
        List<Employee> exceptionEmployees = new ArrayList<>();
        exceptionEmployees.add(secondEmployee);
        exceptionEmployees.add(thirdEmployee);
        given (employeeDao.findByGender("male")).willReturn(exceptionEmployees);
        //when
        List<Employee> employeesFromDb = employeeService.queryEmployeeByGender("male");
        //then
        assertEquals(2, employeesFromDb.size());
        assertEquals(false, employeesFromDb.contains(firstEmployee));
    }

    @Test
    void should_find_employee_when_query_by_id_given_employee_id() {
        //given
        Employee exceptionEmployee = new Employee("1", "Susan", 20, "Female", 8000, "");
        //when
        given (employeeDao.findById("1")).willReturn(Optional.of(exceptionEmployee));
        //then
        Employee employeeFromDb =employeeService.queryEmployeeById("1");
        assertEquals(employeeFromDb,exceptionEmployee);
    }

    @Test
    void should_return_page_one_employees_when_query_by_page_given_page_and_page_size() {
        //given
        List<Employee> exceptionEmployees = new ArrayList<>();
        Employee firstEmployee = new Employee("1", "Susan", 20, "Female", 8000, "");
        Employee secondEmployee = new Employee("1", "Mathew", 20, "Female", 9000, "");
        Employee thirdEmployee = new Employee("1", "Sarah", 25, "Female", 18000, "");
        Employee fourthEmployee = new Employee("1", "Mike", 23, "male", 12000, "");
        exceptionEmployees.add(thirdEmployee);
        exceptionEmployees.add(fourthEmployee);
        int page = 2, pageSize = 2;
        PageImpl<Employee> employees = new PageImpl<Employee>(exceptionEmployees);
        given(employeeDao.findAll(PageRequest.of(page - 1, pageSize))).willReturn(employees);
        //when
        Page<Employee> employeeByPage = employeeService.findEmployeeByPage(page - 1, pageSize);
        //then
        assertThat(employeeByPage).isEqualTo(employees);
        assertThat(employeeByPage.getContent().get(0)).isEqualTo(thirdEmployee);
    }

    @Test
    void should_add_employee_in_repository_when_insert_employee_given_message_of_employee() {
        //given
        Employee firstEmployee = new Employee("1", "Susan", 20, "Female", 8000, "");
        given(employeeDao.save(firstEmployee)).willReturn(firstEmployee);
        //when
        Employee insertEmployee = employeeService.insertEmployee(firstEmployee);
        //then
        assertThat(insertEmployee).isEqualTo(firstEmployee);
    }
    @Test
    void should_count_down_the_employee_when_delete_employee_by_id_given_employee_id() {
        //given
        Employee firstEmployee = new Employee("1", "Susan", 20, "Female", 8000, "");
        given(employeeDao.findById("1")).willReturn(Optional.of(firstEmployee));
        employeeService.removeEmployee(firstEmployee.getId());
        verify(employeeDao, times(1)).delete(firstEmployee);
        //when
        //then
    }
}