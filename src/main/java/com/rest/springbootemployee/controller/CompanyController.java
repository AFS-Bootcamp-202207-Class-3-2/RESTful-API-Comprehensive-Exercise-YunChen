package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.Dto.CompanyResponse;
import com.rest.springbootemployee.Dto.EmployeeResponse;
import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.services.CompaniesService;
import com.rest.springbootemployee.utils.CompanyMapper;
import com.rest.springbootemployee.utils.Constant;
import com.rest.springbootemployee.utils.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {


    @Autowired
    private CompaniesService companiesService;

    @GetMapping
    public List<CompanyResponse> queryAllCompanies() {
        return CompanyMapper.toResponse(companiesService.queryAllCompanies());
    }

    @GetMapping("/{id}")
    public CompanyResponse queryCompanyById(@PathVariable String id) {
        return CompanyMapper.toResponse(companiesService.queryCompanyById(id));
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> queryEmployeesInCompanyById(@PathVariable String id) {
        return EmployeeMapper.toResponse(companiesService.queryEmployeesInCompanyById(id));
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<Company> queryCompanyPage(@RequestParam("page") int page,
                                          @RequestParam("pageSize") int pageSize) {
        return companiesService.queryCompanyPage(page, pageSize);
    }

    @PostMapping
    public Company insertCompany(@RequestBody Company company) {
        return companiesService.insertCompany(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable("id") String companyId,
                                 @RequestBody Company company) {
        return companiesService.updateCompany(companyId, company);
    }

    @DeleteMapping("/{id}")
    public Company deleteCompanyById(@PathVariable("id") String companyId) {
        return companiesService.deleteCompanyById(companyId);

    }
}
