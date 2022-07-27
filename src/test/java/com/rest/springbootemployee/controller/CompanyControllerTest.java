package com.rest.springbootemployee.controller;

import com.alibaba.fastjson.JSON;
import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.mapper.CompaniesRepository;
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

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    CompaniesRepository companiesRepository;
    @BeforeEach
    void prepareForTest() {
        companiesRepository.setNextId("1");
        companiesRepository.getCompanies().clear();
    }

    @Autowired
    MockMvc client;

    @Test
    void should_return_all_companies_when_query_all_companies_given_query() throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("1", "oocl", new ArrayList<>());
        companiesRepository.insertCompany(firstCompany);
        companiesRepository.insertCompany(secondCompany);
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
        companiesRepository.insertCompany(firstCompany);
        companiesRepository.insertCompany(secondCompany);
        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}","2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName",equalTo("aaal")));
        //then
    }

    @Test
    void should_return_last_twice_companies_when_query_company_by_page_given_page_and_page_size()throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("2", "aaal", new ArrayList<>());
        Company thirdCompany = new Company("3", "abc", new ArrayList<>());
        Company fourthCompany = new Company("4", "ddd", new ArrayList<>());
        companiesRepository.insertCompany(firstCompany);
        companiesRepository.insertCompany(secondCompany);
        companiesRepository.insertCompany(thirdCompany);
        companiesRepository.insertCompany(fourthCompany);
        //when
        client.perform(MockMvcRequestBuilders.get("/companies")
                .param("page", "2")
                .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
        //then
    }


    @Test
    void should_return_insert_success_when_insert_company_given_company()throws Exception {

        String jsonCompany = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"companyName\": \"spring\",\n" +
                "    \"employees\": [\n" +
                "      {\n" +
                "        \"id\": \"1\",\n" +
                "        \"name\": \"Mike\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"Female\",\n" +
                "        \"salary\": 6000,\n" +
                "        \"companyName\": \"spring\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"4\",\n" +
                "        \"name\": \"czy\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"male\",\n" +
                "        \"salary\": 18000,\n" +
                "        \"companyName\": \"spring\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }";
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        Company secondCompany = new Company("2", "aaal", new ArrayList<>());
        companiesRepository.insertCompany(firstCompany);
        companiesRepository.insertCompany(secondCompany);
        //when
        client.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCompany));
        //then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

}
