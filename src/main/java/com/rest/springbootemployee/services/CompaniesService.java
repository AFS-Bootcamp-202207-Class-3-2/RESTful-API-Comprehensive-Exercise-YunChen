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
        Optional<Company> optionalCompany = companyDao.findById(companyId);
        if (optionalCompany.isPresent()) {
            return optionalCompany.get();
        }
        throw new CompanyNotFindException();
    }

    public List<Employee> queryEmployeesInCompanyById(String companyId) {
        Optional<Company> optionalCompany = companyDao.findById(companyId);
        if (optionalCompany.isPresent()) {
            return optionalCompany.get().getEmployees();
        }
        throw new CompanyNotFindException();
    }

    public List<Company> queryCompanyPage(int page, int pageSize) {
        Page<Company> allCompanies = companyDao.findAll(PageRequest.of(page, pageSize));
        return allCompanies.getContent();
    }

    public Company insertCompany(Company company) {
        return companyDao.saveAndFlush(company);
    }

    public Company updateCompany(String id,Company company) {
        Optional<Company> optionalCompany = companyDao.findById(id);
        if (optionalCompany.isPresent()) {
            return companyDao.save(company);
        }
        throw new CompanyNotFindException();
    }

    public Boolean deleteCompanyById(String id) {
        Optional<Company> optionalCompany = companyDao.findById(id);
        if (optionalCompany.isPresent()) {
             companyDao.delete(optionalCompany.get());
            return true;
        }
        throw new CompanyNotFindException();
    }
}
