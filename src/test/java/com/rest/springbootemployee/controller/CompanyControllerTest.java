package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.CompanyDao;
import com.rest.springbootemployee.mapper.EmployeeDao;
import com.rest.springbootemployee.services.CompaniesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompanyControllerTest {
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
        Company firstCompany = new Company("", "oocl", null);
        Company secondCompany = new Company("", "oocl", null);
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
        Company firstCompany = new Company("", "oocl", new ArrayList<>());
        Company secondCompany = new Company("", "aaal", new ArrayList<>());
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
        Company firstCompany = new Company("", "oocl", new ArrayList<>());
        Company secondCompany = new Company("", "aaal", new ArrayList<>());
        Company thirdCompany = new Company("", "abc", new ArrayList<>());
        Company fourthCompany = new Company("", "ddd", new ArrayList<>());
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
                "    \"companyName\": \"spring\"\n" +
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
        Company firstCompany = new Company("", "oocl", new ArrayList<>());
        Company secondCompany = new Company("", "aaal", new ArrayList<>());
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
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", companyFromDb.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", contains(
                        firstSave.getId(),
                        secondSave.getId(),
                        thirdSave.getId(),
                        fourthSave.getId())));
        //then
    }


    @Test
    void should_change_name_of_company_when_update_given_update_msg()throws Exception {
        String jsonCompany = "{\n" +
                "    \"companyName\": \"spring\"\n" +
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
