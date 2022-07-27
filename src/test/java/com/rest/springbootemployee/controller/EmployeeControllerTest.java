package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
        employeeRepository.setNextId("1");
        employeeRepository.getEmployees().clear();
    }

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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]name").value("Salay"))
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
    

}
