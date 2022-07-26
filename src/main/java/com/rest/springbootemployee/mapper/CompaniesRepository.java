package com.rest.springbootemployee.mapper;


import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.CompanyNotFindException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
@DependsOn("employeeRepository")
public class CompaniesRepository implements InitializingBean {
    private List<Company> companies = new ArrayList<>();

    private String nextId;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        companies.addAll(
                Arrays.asList(
                        new Company("1", "spring", new ArrayList<>()),
                        new Company("2", "oocl", new ArrayList<>()
                        ))
        );
        for (Company company : companies) {
            String companyName = company.getCompanyName();
            company.getEmployees().addAll(
                    employeeRepository.getAllEmployees().stream()
                            .filter(employee -> employee.getCompanyName().equals(companyName))
                            .collect(Collectors.toList())
            );
        }
        nextId = String.valueOf(companies.size() + 1);
    }

    public List<Company> queryAllCompanies() {
        return companies;
    }

    public Company queryCompanyById(String companyId) {
        return companies.stream().
                filter(company -> company.getId().equals(companyId)).
                findFirst().orElseThrow(CompanyNotFindException::new);
    }

    public List<Employee> queryEmployeesInCompanyById(String companyId) {
        return companies.stream().
                filter(company -> company.getId().equals(companyId)).
                findFirst().orElseThrow(CompanyNotFindException::new).
                getEmployees();
    }

    public List<Company> queryCompanyPage(int page, int pageSize) {
        return companies.stream()
                .skip((long) page - 1)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public boolean insertCompany(Company company) {
        company.setId(nextId);
        nextId = String.valueOf(Integer.parseInt(nextId));
        return companies.add(company);
    }

    public boolean updateCompany(String companyId,Company company) {
        try {
            Company companyFromDb = queryCompanyById(companyId);
            companyFromDb.setCompanyName(company.getCompanyName());
            return companies.add(company);
        } catch (CompanyNotFindException exception) {
            return false;
        }
    }

    public boolean deleteCompanyById(String companyId) {
        try {
            Company companyFromDb = queryCompanyById(companyId);
            return companies.remove(companyFromDb);
        } catch (CompanyNotFindException exception) {
            return false;
        }
    }
}
