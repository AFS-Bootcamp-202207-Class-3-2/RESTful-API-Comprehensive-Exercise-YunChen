package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Company;
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

}
