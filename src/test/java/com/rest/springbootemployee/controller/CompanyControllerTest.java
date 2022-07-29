package com.rest.springbootemployee.controller;

import com.alibaba.fastjson.JSON;
import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.CompaniesRepository;
import com.rest.springbootemployee.mapper.CompanyDao;
import com.rest.springbootemployee.mapper.EmployeeDao;
import com.rest.springbootemployee.services.CompaniesService;
import com.rest.springbootemployee.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {



    @Autowired
    CompaniesRepository companiesRepository;
    @BeforeEach
    void prepareForTest() {
        employeeDao.deleteAll();
        companyDao.deleteAll();
    }

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    CompanyDao companyDao;

    @Autowired
    CompaniesService companiesService;

    @Autowired
    MockMvc client;

    @Test
    void should_return_all_companies_when_query_all_companies_given_query() throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("1", "oocl", new ArrayList<>());
        companyDao.saveAndFlush(firstCompany);
        companyDao.saveAndFlush(secondCompany);
        //when
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
        //then
    }

    @Test
    void should_return_right_company_when_query_by_id_given_company_id() throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("2", "aaal", new ArrayList<>());
        companyDao.saveAndFlush(firstCompany);
        Company secondCompanySave = companyDao.saveAndFlush(secondCompany);
        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",secondCompanySave.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName",equalTo("aaal")));
        //then
    }

    @Test
    void should_return_not_find_message_when_query_by_id_given_company_id() throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("2", "aaal", new ArrayList<>());
        companyDao.saveAndFlush(firstCompany);
        companyDao.saveAndFlush(secondCompany);
        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}","3"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        //then
    }

    @Test
    void should_return_last_twice_companies_when_query_company_by_page_given_page_and_page_size()throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("2", "aaal", new ArrayList<>());
        Company thirdCompany = new Company("3", "abc", new ArrayList<>());
        Company fourthCompany = new Company("4", "ddd", new ArrayList<>());
        companyDao.saveAndFlush(firstCompany);
        companyDao.saveAndFlush(secondCompany);
        companyDao.saveAndFlush(thirdCompany);
        companyDao.saveAndFlush(fourthCompany);
        //when
        client.perform(MockMvcRequestBuilders.get("/companies")
                .param("page", "1")
                .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)));
        //then
    }


    @Test
    void should_return_insert_company_msg_when_insert_company_given_company()throws Exception {

        String jsonCompany = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"companyName\": \"spring\",\n" +
                "    \"employees\": [ "+
                "    ]\n" +
                "  }";
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("2", "aaal", new ArrayList<>());
        companyDao.saveAndFlush(firstCompany);
        companyDao.saveAndFlush(secondCompany);
        //when
        client.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCompany));
        //then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }
    @Test
    public void should_return_company_employees_when_query_employees_by_companyee_id_given_company_id()throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("2", "aaal", new ArrayList<>());
        Company companyFromDb = companyDao.saveAndFlush(firstCompany);
        companyDao.saveAndFlush(secondCompany);
        Employee firstEmployee = new Employee("", "YunChen", 18, "male", 18000, companyFromDb.getCompanyName());
        firstEmployee.setCompanyId(companyFromDb.getId());
        Employee secondEmployee = new Employee("", "Sarah", 18, "Female", 22000, companyFromDb.getCompanyName());
        secondEmployee.setCompanyId(companyFromDb.getId());
        Employee thirdEmployee = new Employee("", "Mike", 18, "Female", 22000, companyFromDb.getCompanyName());
        thirdEmployee.setCompanyId(companyFromDb.getId());
        Employee fourthEmployee = new Employee("", "Jack", 18, "Female", 22000, companyFromDb.getCompanyName());
        fourthEmployee.setCompanyId(companyFromDb.getId());
        Employee firstSave = employeeDao.saveAndFlush(firstEmployee);
        Employee secondSave = employeeDao.saveAndFlush(secondEmployee);
        Employee thirdSave = employeeDao.saveAndFlush(thirdEmployee);
        Employee fourthSave = employeeDao.saveAndFlush(fourthEmployee);
        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees",companyFromDb.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));
        List<Employee> employees = companiesService.queryEmployeesInCompanyById(companyFromDb.getId());
        List<String> ids = employees.stream().map((Employee::getId)).collect(Collectors.toList());
        //then
        System.out.println(employees.size());
        assertThat(ids).contains(firstSave.getId(),secondSave.getId(),thirdSave.getId(),fourthSave.getId());
    }

    void saveBatchEmployees(Employee... employees) {
        for (int i = 0; i < employees.length; i++) {
            employeeDao.save(employees[i]);
        }
    }

    @Test
    void should_change_name_of_company_when_update_given_update_msg()throws Exception {
        String jsonCompany = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"companyName\": \"spring\",\n" +
                "    \"employees\": [" +
                "    ]\n" +
                "  }";
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("2", "aaal", new ArrayList<>());
        companyDao.saveAndFlush(firstCompany);
        Company updateCompany = companyDao.saveAndFlush(secondCompany);
        //when
        client.perform(MockMvcRequestBuilders.put("/companies/{id}",updateCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCompany));
        //then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",updateCompany.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName", equalTo("spring")));
    }


    @Test
    void should_decrease_number_when_delete_given_company_id()throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("2", "aaal", new ArrayList<>());
        companyDao.saveAndFlush(firstCompany);
        Company deleteCompany = companyDao.saveAndFlush(secondCompany);
        //when
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}",deleteCompany.getId()));
        //then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }
}
