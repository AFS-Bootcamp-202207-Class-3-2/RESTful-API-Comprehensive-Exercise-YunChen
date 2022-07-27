package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompaniesService {


    @Autowired
    private CompaniesRepository companiesRepository;
    public List<Company> queryAllCompanies() {
        return companiesRepository.queryAllCompanies();
    }

    public Company queryCompanyById(String companyId) {
        return companiesRepository.queryCompanyById(companyId);
    }

    public List<Employee> queryEmployeesInCompanyById(String id) {
        return companiesRepository.queryEmployeesInCompanyById(id);
    }

    public List<Company> queryCompanyPage(int page, int pageSize) {
        return companiesRepository.queryCompanyPage(page, pageSize);
    }

    public Boolean insertCompany(Company company) {
        return companiesRepository.insertCompany(company);
    }
}
