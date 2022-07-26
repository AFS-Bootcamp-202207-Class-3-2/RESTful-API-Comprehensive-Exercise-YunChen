package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.mapper.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/companies")
public class CompanyController {

    @Autowired
    private CompaniesRepository companiesRepository;
    @GetMapping
    public List<Company> queryAllCompanies() {
        return companiesRepository.queryAllCompanies();
    }
}
