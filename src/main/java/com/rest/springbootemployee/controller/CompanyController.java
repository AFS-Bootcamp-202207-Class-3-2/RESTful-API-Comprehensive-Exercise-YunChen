package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.Dto.CompanyRequest;
import com.rest.springbootemployee.Dto.CompanyResponse;
import com.rest.springbootemployee.Dto.EmployeeResponse;
import com.rest.springbootemployee.Dto.PageResponse;
import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.services.CompaniesService;
import com.rest.springbootemployee.utils.MapperDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {


    @Autowired
    private CompaniesService companiesService;

    @GetMapping
    public List<CompanyResponse> queryAllCompanies() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return MapperDtoUtil.<CompanyResponse, Company>toResponse(
                companiesService.queryAllCompanies()
                , CompanyResponse.class);
    }

    @GetMapping("/{id}")
    public CompanyResponse queryCompanyById(@PathVariable String id) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return MapperDtoUtil.<CompanyResponse, Company>toResponse(companiesService.queryCompanyById(id),
                CompanyResponse.class);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> queryEmployeesInCompanyById(@PathVariable String id) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return MapperDtoUtil.<EmployeeResponse, Employee>toResponse(companiesService.queryEmployeesInCompanyById(id)
                , EmployeeResponse.class);
    }

    @GetMapping(params = {"page", "pageSize"})
    public PageResponse<Company> queryCompanyPage(@RequestParam("page") int page,
                                                  @RequestParam("pageSize") int pageSize) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return MapperDtoUtil.<Company>toResponse(companiesService.queryCompanyPage(page, pageSize)
                , Company.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompanyResponse insertCompany(@RequestBody CompanyRequest companyRequest) throws InstantiationException, IllegalAccessException, NoSuchFieldException {

        return MapperDtoUtil.<CompanyResponse, Company>toResponse(
                companiesService.insertCompany(
                        MapperDtoUtil.<Company, CompanyRequest>toRequest(companyRequest, Company.class)
                ), CompanyResponse.class
        );
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompany(@PathVariable("id") String companyId,
                                         @RequestBody CompanyRequest companyRequest) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return MapperDtoUtil.<CompanyResponse, Company>toResponse(
                companiesService.updateCompany(companyId,MapperDtoUtil.<Company,CompanyRequest>toRequest(companyRequest,Company.class)),
                CompanyResponse.class
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable("id") String companyId) {
        companiesService.deleteCompanyById(companyId);
    }
}
