package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.CompanyDao;
import com.rest.springbootemployee.mapper.EmployeeDao;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    MockMvc client;

    @BeforeEach
    public void beforePrepare() {
        employeeDao.deleteAll();
        Company firstCompany = new Company("", "abc", new ArrayList<>());
        Employee firstEmployee = new Employee("", "YunChen", 18, "male", 18000, firstCompany.getCompanyName());
        Employee secondEmployee = new Employee("", "Sarah", 18, "Female", 22000, firstCompany.getCompanyName());
        Employee thirdEmployee = new Employee("", "Sarah", 18, "Female", 22000, firstCompany.getCompanyName());
        Employee fourthEmployee = new Employee("", "Sarah", 18, "Female", 22000, firstCompany.getCompanyName());
        Company saveCompany = companyDao.save(firstCompany);
//        companyDao.findById()
    }

    @Autowired
    CompanyDao companyDao;

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void should_get_all_employees_when_perform_get_given_employees() throws Exception {
        //given
        employeeRepository.insert(new Employee("6", "Salay", 22, "Female", 10000, ""));
        //when
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Salay"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value(""));
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
                "    \"companyName\": \"oocl\"\n" +
                "  }";
        //when
        client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployee))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("oocl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(8000));
        //then
        List<Employee> allEmployees = employeeRepository.getAllEmployees();
        assertThat(allEmployees, hasSize(1));
        assertThat(allEmployees.get(0).getId(), equalTo("1"));
        assertThat(allEmployees.get(0).getName(), equalTo("Lily"));
        assertThat(allEmployees.get(0).getAge(), equalTo(20));
        assertThat(allEmployees.get(0).getGender(), equalTo("Female"));
        assertThat(allEmployees.get(0).getCompanyName(), equalTo("oocl"));
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
                "    \"companyName\": \"oocl\"\n" +
                "  }";
        String maleEmployee = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"John\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 8000,\n" +
                "    \"companyName\": \"oocl\"\n" +
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary", equalTo(8000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName", equalTo("oocl")));
        //then
    }

    @Test
    void should_return_employee_when_query_by_id_given_employee_id() throws Exception {
        //given
        employeeRepository.insert(new Employee(
                "1",
                "Sarah",
                12,
                "Female",
                2000,
                "abc"
        ));
        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{id}","1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",equalTo("Sarah")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age",equalTo(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender",equalTo("Female")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary",equalTo(2000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName",equalTo("abc")));
        //then
    }


    @Test
    void should_return_third_fourth_employee_when_find_by_page_given_page_2_page_size_2() throws Exception {
        //given
        Employee firstEmployee = new Employee("1", "Sarah", 12, "Female", 42000, "abc");
        Employee secondEmployee = new Employee("1", "Mathew", 12, "Female", 22000, "abc");
        Employee thirdEmployee = new Employee("1", "Hang", 12, "Female", 12000, "abc");
        Employee fourthEmployee = new Employee("1", "Done", 12, "Female", 2000, "abc");
        employeeRepository.insert(firstEmployee);
        employeeRepository.insert(secondEmployee);
        employeeRepository.insert(thirdEmployee);
        employeeRepository.insert(fourthEmployee);
        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page", "2")
                        .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
        //then
    }


    @Test
    void should_increase_employee_number_when_delete_exist_employee_given_delete_id() throws Exception {
        //given
        Employee firstEmployee = new Employee("1", "Sarah", 12, "Female", 42000, "abc");
        Employee secondEmployee = new Employee("1", "Mathew", 12, "Female", 22000, "abc");
        Employee thirdEmployee = new Employee("1", "Hang", 12, "Female", 12000, "abc");
        Employee fourthEmployee = new Employee("1", "Done", 12, "Female", 2000, "abc");
        employeeRepository.insert(firstEmployee);
        employeeRepository.insert(secondEmployee);
        employeeRepository.insert(thirdEmployee);
        employeeRepository.insert(fourthEmployee);
        //when
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}","2"));
        //then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    void should_update_salay_50000_when_update_employee_given_id_and_update_message() throws Exception {
        //given
        Employee firstEmployee = new Employee("1", "Sarah", 12, "Female", 42000, "abc");
        Employee secondEmployee = new Employee("2", "Mathew", 12, "Female", 22000, "abc");
        Employee thirdEmployee = new Employee("1", "Hang", 12, "Female", 12000, "abc");
        Employee fourthEmployee = new Employee("1", "Done", 12, "Female", 2000, "abc");
        employeeRepository.insert(firstEmployee);
        employeeRepository.insert(secondEmployee);
        employeeRepository.insert(thirdEmployee);
        employeeRepository.insert(fourthEmployee);
        String updateEmployeeMsg = "{\n" +
                "    \"id\": \"2\",\n" +
                "    \"name\": \"Mathew\",\n" +
                "    \"age\": 12,\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"salary\": 50000,\n" +
                "    \"companyName\": \"abc\"\n" +
                "  }";
        //when
        client.perform(MockMvcRequestBuilders.put("/employees/{id}","2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEmployeeMsg));
        //then
        client.perform(MockMvcRequestBuilders.get("/employees/{id}","2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary",equalTo(50000)));
    }


    

}
