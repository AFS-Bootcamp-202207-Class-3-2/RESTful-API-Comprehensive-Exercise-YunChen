package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.CompanyDao;
import com.rest.springbootemployee.mapper.EmployeeDao;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {


    @Mock
    EmployeeDao employeeDao;
    @InjectMocks
    EmployeeService employeeService;

    Company companyFromDb;
    @BeforeEach
    public void beforePrepare() {
        employeeDao.deleteAll();
//        companyDao.deleteAll();
//        Company company = new Company("", "spring", null);
//        Company companyFromDb = companyDao.saveAndFlush(company);
//        this.companyFromDb = companyFromDb;
    }

    private void saveFourEmployees() {
        Employee firstEmployee = new Employee("", "YunChen", 18, "male", 18000, companyFromDb.getCompanyName());
        firstEmployee.setCompanyId(companyFromDb.getId());
        Employee secondEmployee = new Employee("", "Sarah", 18, "Female", 22000, companyFromDb.getCompanyName());
        secondEmployee.setCompanyId(companyFromDb.getId());
        Employee thirdEmployee = new Employee("", "Mike", 18, "Female", 22000, companyFromDb.getCompanyName());
        thirdEmployee.setCompanyId(companyFromDb.getId());
        Employee fourthEmployee = new Employee("", "Jack", 18, "Female", 22000, companyFromDb.getCompanyName());
        fourthEmployee.setCompanyId(companyFromDb.getId());
        employeeService.insertEmployee(firstEmployee);
        employeeService.insertEmployee(secondEmployee);
        employeeService.insertEmployee(fourthEmployee);
        employeeService.insertEmployee(thirdEmployee);
    }

//    @Autowired
//    CompanyDao companyDao;




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
//        given(employeeRepository.findByPage(page, pageSize)).willReturn(exceptionEmployees);
        //when
        List<Employee> employeeByPage = employeeService.findEmployeeByPage(page, pageSize).getContent();
        //then
        assertThat(employeeByPage).contains(thirdEmployee, fourthEmployee);
    }

    @Test
    void should_add_employee_in_repository_when_insert_employee_given_message_of_employee() {
        //given
        Employee firstEmployee = new Employee("1", "Susan", 20, "Female", 8000, "");
        given(employeeDao.saveAndFlush(firstEmployee)).willReturn(firstEmployee);
        //when
        Employee insertEmployee = employeeService.insertEmployee(firstEmployee);
        //then
        assertThat(insertEmployee).isEqualTo(firstEmployee);
    }


    @Test
    void should_count_down_the_employee_when_delete_employee_by_id_given_employee_id() {
        //given
        Employee firstEmployee = new Employee("1", "Susan", 20, "Female", 8000, "");
//        given(employeeRepository.deleteEmployee("1")).willReturn(true);
        //when
//        Boolean isInsertEmployee = employeeService.removeEmployee(firstEmployee.getId());
        //then
//        assertThat(isInsertEmployee).isEqualTo(true);
    }


}