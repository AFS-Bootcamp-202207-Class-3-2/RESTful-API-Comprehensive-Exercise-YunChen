package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.Dto.CompanyPageResponse;
import com.rest.springbootemployee.Dto.CompanyRequest;
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
import org.springframework.http.HttpStatus;
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
    public CompanyPageResponse queryCompanyPage(@RequestParam("page") int page,
                                                @RequestParam("pageSize") int pageSize) {
        return CompanyMapper.toResponse(companiesService.queryCompanyPage(page, pageSize));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompanyResponse insertCompany(@RequestBody CompanyRequest companyRequest) {
        return CompanyMapper.toResponse(
                companiesService.insertCompany(CompanyMapper.toRequest(companyRequest)));
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompany(@PathVariable("id") String companyId,
                                 @RequestBody CompanyRequest companyRequest) {
        return CompanyMapper.toResponse(
                companiesService.updateCompany(companyId, CompanyMapper.toRequest(companyRequest))
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable("id") String companyId) {
         companiesService.deleteCompanyById(companyId);
    }
}
