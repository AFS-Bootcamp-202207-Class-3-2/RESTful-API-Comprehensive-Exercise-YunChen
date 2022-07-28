package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.CompanyNotFindException;
import com.rest.springbootemployee.mapper.CompaniesRepository;
import com.rest.springbootemployee.mapper.CompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompaniesService {


    @Autowired
    private CompanyDao companyDao;
    public List<Company> queryAllCompanies() {
        return companyDao.findAll();
    }

    public Company queryCompanyById(String companyId) {
        return companyDao.findById(companyId)
                .orElseThrow(CompanyNotFindException::new);
    }

    public List<Employee> queryEmployeesInCompanyById(String companyId) {
        Company company = companyDao.findById(companyId).orElseThrow(CompanyNotFindException::new);
        return company.getEmployees();
    }

    public Page<Company> queryCompanyPage(int page, int pageSize) {
        return companyDao.findAll(PageRequest.of(page - 1, pageSize));
    }

    public Company insertCompany(Company company) {
        Company companyInsert = companyDao.saveAndFlush(company);
        return companyInsert;
    }

    public Company updateCompany(String id,Company company) {
        Company companyFromDb = companyDao.findById(id).orElseThrow(CompanyNotFindException::new);
        company.setId(companyFromDb.getId());
        return companyDao.saveAndFlush(company);
    }

    public Company deleteCompanyById(String id) {
        Company deleteCompany = companyDao.findById(id).orElseThrow(CompanyNotFindException::new);
        companyDao.delete(deleteCompany);
        return deleteCompany;
    }
}
