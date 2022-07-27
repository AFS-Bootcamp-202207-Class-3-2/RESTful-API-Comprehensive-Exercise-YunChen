package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.mapper.CompaniesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;

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

}
