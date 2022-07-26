package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.CompaniesRepository;
import com.rest.springbootemployee.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompaniesRepository companiesRepository;

    @GetMapping
    public List<Company> queryAllCompanies() {
        return companiesRepository.queryAllCompanies();
    }

    @GetMapping("/{id}")
    public Company queryCompanyById(@PathVariable String id) {
        return companiesRepository.queryCompanyById(id);
    }
    @GetMapping("/{id}/employees")
    public List<Employee> queryEmployeesInCompanyById(@PathVariable String id) {
        return companiesRepository.queryEmployeesInCompanyById(id);
    }
    @GetMapping(params = {"page","pageSize"})
    public List<Company> queryCompanyPage(@RequestParam("page") int page,
                                    @RequestParam("pageSize") int pageSize) {
        return companiesRepository.queryCompanyPage(page,pageSize);
    }

    @PostMapping
    public String insertCompany(@RequestBody Company company) {
        return companiesRepository.insertCompany(company) ?
                Constant.ADD_COMPANY_SUCCESS :
                Constant.COMPANY_IS_EXIST;
    }

    @PutMapping("/{id}")
    public String updateCompany(@PathVariable("id") String companyId,
                                @RequestBody Company company) {
        return companiesRepository.updateCompany(companyId,company) ?
                Constant.UPDATE_COMPANY_SUCCESS :
                Constant.COMPANY_IS_NOT_EXIST;
    }

//    @DeleteMapping
//    public String updateCompany(@RequestBody Company company) {
//        return companiesRepository.updateCompany(company) ?
//                Constant.UPDATE_COMPANY_SUCCESS :
//                Constant.COMPANY_IS_NOT_EXIST;
//    }
}
