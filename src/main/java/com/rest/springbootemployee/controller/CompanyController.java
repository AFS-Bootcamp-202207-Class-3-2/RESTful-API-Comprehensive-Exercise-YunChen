package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.services.CompaniesService;
import com.rest.springbootemployee.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {


    @Autowired
    private CompaniesService companiesService;

    @GetMapping
    public List<Company> queryAllCompanies() {
        return companiesService.queryAllCompanies();
    }

    @GetMapping("/{id}")
    public Company queryCompanyById(@PathVariable String id) {
        return companiesService.queryCompanyById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> queryEmployeesInCompanyById(@PathVariable String id) {
        return companiesService.queryEmployeesInCompanyById(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> queryCompanyPage(@RequestParam("page") int page,
                                          @RequestParam("pageSize") int pageSize) {
        return companiesService.queryCompanyPage(page, pageSize);
    }

    @PostMapping
    public String insertCompany(@RequestBody Company company) {
        companiesService.insertCompany(company);
        return Constant.ADD_COMPANY_SUCCESS;
    }

    @PutMapping("/{id}")
    public String updateCompany(@PathVariable("id") String companyId,
                                @RequestBody Company company) {
        return companiesService.updateCompany(companyId, company) ?
                Constant.UPDATE_COMPANY_SUCCESS :
                Constant.COMPANY_IS_NOT_EXIST;
    }

    @DeleteMapping("/{id}")
    public String deleteCompanyById(@PathVariable("id") String companyId) {
        boolean isSuccessDelete = companiesService.deleteCompanyById(companyId);
        return isSuccessDelete ?
                Constant.DELETE_COMPANY_SUCCESS :
                Constant.COMPANY_IS_NOT_EXIST;
    }
}
