package com.rest.springbootemployee.controller;

import com.alibaba.fastjson.JSON;
import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.CompanyDao;
import com.rest.springbootemployee.mapper.EmployeeDao;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import com.rest.springbootemployee.services.EmployeeService;
import com.rest.springbootemployee.utils.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeControllerTest {
    @Autowired
    MockMvc client;

    Company companyFromDb;
    @BeforeEach
    public void beforePrepare() {
        employeeDao.deleteAll();
        companyDao.deleteAll();
        Company company = new Company("", "spring", null);
        Company companyFromDb = companyDao.saveAndFlush(company);
        this.companyFromDb = companyFromDb;
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

    @Autowired
    CompanyDao companyDao;

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeDao employeeDao;

    @Test
    void should_get_all_employees_when_perform_get_given_employees() throws Exception {
        //given
        Employee employee = new Employee("", "Salay", 22, "Female", 10000, "");
        employee.setCompanyId(companyFromDb.getId());
        Employee employeeFromDb = employeeDao.save(employee);
        //when
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Salay"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Female"));
        //then
    }

    @Test
    void should_create_a_new_empLoyee_when_perform_post_given_a_new_employee() throws Exception {
        //given
        String newEmployee = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"Lily\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"salary\": 8000,\n" +
                "    \"companyName\": \""+companyFromDb.getCompanyName()+"\",\n" +
                "    \"company_id\": \""+companyFromDb.getId()+"\"\n" +
                "  }";
        //when
        client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployee))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(companyFromDb.getCompanyName()));
        //then
        List<Employee> allEmployees = employeeService.findALl();
        assertThat(allEmployees, hasSize(1));
        assertThat(allEmployees.get(0).getName(), equalTo("Lily"));
        assertThat(allEmployees.get(0).getAge(), equalTo(20));
        assertThat(allEmployees.get(0).getGender(), equalTo("Female"));
        assertThat(allEmployees.get(0).getCompanyName(), equalTo(companyFromDb.getCompanyName()));
        assertThat(allEmployees.get(0).getSalary(), equalTo(8000));
    }

    
    @Test
    void should_return_all_male_employees_when_perform_get_by_gender_given_employees_and_gender_is_male() throws Exception {
        //given
        String femaleEmployee = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"Lily\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"salary\": 8000,\n" +
                "    \"companyName\": \""+companyFromDb.getCompanyName()+"\",\n" +
                "    \"companyId\": \""+companyFromDb.getId()+"\"\n" +
                "  }";
        String maleEmployee = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"John\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 8000,\n" +
                "    \"companyName\": \""+companyFromDb.getCompanyName()+"\",\n" +
                "    \"companyId\": \""+companyFromDb.getId()+"\"\n" +
                "  }";
        client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(femaleEmployee));
        client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(maleEmployee));
        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                .param("gender","male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", equalTo("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender", equalTo("male")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age", equalTo(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName", equalTo(companyFromDb.getCompanyName())));
        //then
    }

    @Test
    void should_return_employee_when_query_by_id_given_employee_id() throws Exception {
        //given
        Employee employee = new Employee(
                "",
                "Sarah",
                12,
                "Female",
                2000,
                companyFromDb.getCompanyName()
        );
        employee.setCompanyId(companyFromDb.getId());
        Employee saveEmployeeFromDb = employeeDao.save(employee);
        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", saveEmployeeFromDb.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Sarah")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", equalTo(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender", equalTo("Female")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName", equalTo(companyFromDb.getCompanyName())));
        //then
    }


    void addCompanyId(Employee ...employees) {
        for (int i = 0; i < employees.length; i++) {
            employees[i].setCompanyId(companyFromDb.getId());
        }
    }
    @Test
    void should_return_third_fourth_employee_when_find_by_page_given_page_2_page_size_2() throws Exception {
        //given
        Employee firstEmployee = new Employee("1", "Sarah", 12, "Female", 42000, companyFromDb.getCompanyName());
        Employee secondEmployee = new Employee("1", "Mathew", 12, "Female", 22000, companyFromDb.getCompanyName());
        Employee thirdEmployee = new Employee("1", "Hang", 12, "Female", 12000, companyFromDb.getCompanyName());
        Employee fourthEmployee = new Employee("1", "Done", 12, "Female", 2000, companyFromDb.getCompanyName());
        addCompanyId(firstEmployee,secondEmployee,thirdEmployee,fourthEmployee);
        employeeDao.save(firstEmployee);
        employeeDao.save(secondEmployee);
        Employee save = employeeDao.save(thirdEmployee);
        Employee secondSave = employeeDao.save(fourthEmployee);
        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page", "1")
                        .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].name", contains(
                       save.getName(),secondSave.getName())));
        //then
    }


    @Test
    void should_increase_employee_number_when_delete_exist_employee_given_delete_id() throws Exception {
        //given
        Employee firstEmployee = new Employee("", "Sarah", 12, "Female", 42000, companyFromDb.getCompanyName());
        Employee secondEmployee = new Employee("", "Mathew", 12, "Female", 22000, companyFromDb.getCompanyName());
        Employee thirdEmployee = new Employee("", "Hang", 12, "Female", 12000, companyFromDb.getCompanyName());
        Employee fourthEmployee = new Employee("", "Done", 12, "Female", 2000, companyFromDb.getCompanyName());
        addCompanyId(firstEmployee,secondEmployee,thirdEmployee,fourthEmployee);
        employeeDao.save(firstEmployee);
        Employee saveSecondEmployee = employeeDao.save(secondEmployee);
        employeeDao.save(thirdEmployee);
        employeeDao.save(fourthEmployee);
        //when
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}",saveSecondEmployee.getId()));
        //then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    void should_update_salay_50000_when_update_employee_given_id_and_update_message() throws Exception {
        //given
        Employee firstEmployee = new Employee("1", "Sarah", 12, "Female", 42000, companyFromDb.getCompanyName());
        Employee secondEmployee = new Employee("2", "Mathew", 12, "Female", 22000, companyFromDb.getCompanyName());
        Employee thirdEmployee = new Employee("1", "Hang", 12, "Female", 12000, companyFromDb.getCompanyName());
        Employee fourthEmployee = new Employee("1", "Done", 12, "Female", 2000, companyFromDb.getCompanyName());
        addCompanyId(firstEmployee,secondEmployee,thirdEmployee,fourthEmployee);
        employeeDao.save(firstEmployee);
        Employee saveSecondEmployee = employeeDao.save(secondEmployee);
        employeeDao.save(thirdEmployee);
        employeeDao.save(fourthEmployee);
        String updateEmployeeMsg = "{\n" +
                "    \"name\": \"Mathew\",\n" +
                "    \"age\": 12,\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"salary\": 50000,\n" +
                "    \"companyName\": \"abc\",\n" +
                "    \"companyId\": \"" + companyFromDb.getId() + "\"\n" +
                "  }";
        //when
        client.perform(MockMvcRequestBuilders.put("/employees/{id}",saveSecondEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEmployeeMsg));
        //then
        Employee employee = employeeDao.findById(saveSecondEmployee.getId()).get();
        assertThat(employee.getSalary(),equalTo(50000));
    }


    

}
