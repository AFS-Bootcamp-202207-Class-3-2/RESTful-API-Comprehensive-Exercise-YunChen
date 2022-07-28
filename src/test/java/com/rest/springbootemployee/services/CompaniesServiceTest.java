package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.CompanyNotFindException;
import com.rest.springbootemployee.mapper.CompaniesRepository;
import com.rest.springbootemployee.mapper.CompanyDao;
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
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompaniesServiceTest {
    @Mock
    CompaniesRepository companiesRepository;
//    @Autowired
    @Mock
    CompanyDao companyDao;
    @InjectMocks
    CompaniesService companiesService;

    @BeforeEach
    void initData() {
        companyDao.deleteAll();
    }

    @Test
    void should_get_all_companies_when_query_all_given_request()throws Exception {
        //given
        List<Company> exceptCompanies = new ArrayList<>();
        Company firstCompany = new Company("1", "ooal", new ArrayList<>());
        Company secondCompany = new Company("1", "oobl", new ArrayList<>());
        Company thirdCompany = new Company("1", "oocl", new ArrayList<>());
        exceptCompanies.add(firstCompany);
        exceptCompanies.add(secondCompany);
        exceptCompanies.add(thirdCompany);
        //when
        given(companyDao.findAll()).willReturn(exceptCompanies);
        //then
        List<Company> companiesFromDb = companiesService.queryAllCompanies();
        assertThat(companiesFromDb).isEqualTo(exceptCompanies);
    }

    @Test
    void should_return_right_company_when_query_by_id_given_company_id()throws Exception {
        //given
        Company secondCompany = new Company("1", "oobl", new ArrayList<>());
        //when
        given(companyDao.findById("2")).willReturn(Optional.of(secondCompany));
        //then
        Company companyFromDb = companiesService.queryCompanyById("2");
        assertThat(companyFromDb).isEqualTo(secondCompany);
    }

    @Test
    void should_return_company_employees_data_when_query_company_employees_by_company_id_given_company_id()throws Exception {
        //given
        Company company = new Company("1", "oobl", null);
        List<Employee> prepareEmployees = new ArrayList<>();
        Employee firstEmployee = new Employee("1", "Susan", 23, "Female", 9000, "");
        Employee secondEmployee = new Employee("1", "Mathew", 23, "Female", 12000, "");
        prepareEmployees.add(firstEmployee);
        prepareEmployees.add(secondEmployee);
        company.setEmployees(prepareEmployees);
        given(companyDao.findById("1")).willReturn(Optional.of(company));
        //when
        List<Employee> companyEmployeesFromDb = companiesService.queryEmployeesInCompanyById("1");
        //then
        assertThat(companyEmployeesFromDb).isEqualTo(prepareEmployees);
    }

    @Test
    void should_get_page_second_companies_when_query_by_page_given_page_2_page_size_2()throws Exception {
        //given
        List<Company> exceptCompanies = new ArrayList<>();
        Company firstCompany = new Company("1", "ooal", new ArrayList<>());
        Company secondCompany = new Company("1", "oobl", new ArrayList<>());
        Company thirdCompany = new Company("1", "oocl", new ArrayList<>());
        Company fourthCompany = new Company("1", "oodl",new ArrayList<>());
        exceptCompanies.add(firstCompany);
        exceptCompanies.add(secondCompany);
        exceptCompanies.add(thirdCompany);
        exceptCompanies.add(fourthCompany);
        given(companiesRepository.queryCompanyPage(2,2)).willReturn(exceptCompanies);
        //when
        List<Company> companyEmployeesFromDb = companiesService.queryCompanyPage(2,2);
        //then
        assertThat(companyEmployeesFromDb).isEqualTo(exceptCompanies);
    }
    @Test
    void should_increase_company_when_insert_company_given_company_msg()throws Exception {
        //given
        Company firstCompany = new Company("1", "ooal", new ArrayList<>());
        given(companyDao.saveAndFlush(firstCompany)).willReturn(firstCompany);
        //when
        Company insertCompany = companiesService.insertCompany(firstCompany);
        //then
        assertThat(firstCompany).isEqualTo(insertCompany);
    }

    @Test
    void should_get_true_when_update_company_given_company_msg()throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
        given(companiesRepository.updateCompany("1",firstCompany)).willReturn(true);
        //when
        Company updateCompany = companiesService.updateCompany("1",firstCompany);
        //then
        assertThat(updateCompany).isEqualTo(firstCompany);
    }

    @Test
    void should_get_true_when_delete_company_given_company_id()throws Exception {
        //given
        Company firstCompany = new Company("1", "oocl", new ArrayList<>());
//        given(companyDao.delete(firstCompany)).willReturn(firstCompany);
//        //when
//        Boolean isDelete = companiesService.deleteCompanyById("1");
        //then
//        assertThat(true).isEqualTo(isDelete);
    }
}
