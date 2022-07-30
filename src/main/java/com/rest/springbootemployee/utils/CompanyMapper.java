package com.rest.springbootemployee.utils;

import com.rest.springbootemployee.Dto.*;
import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class CompanyMapper {
    public static List<CompanyResponse> toResponse(List<Company> companyList) {
        List<CompanyResponse> returnList = new ArrayList<>();
        for (int idx = 0; idx < companyList.size(); idx++) {
            returnList.add(toResponse(companyList.get(idx)));
        }
        return returnList;
    }

    public static CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        return companyResponse;
    }
    public static CompanyPageResponse toResponse(Page<Company> companyOfPage) {
        CompanyPageResponse companyPageResponse = new CompanyPageResponse();
        List<Company> content = companyOfPage.getContent();
        List<CompanyResponse> mapperContent = new ArrayList<>();
        for (int idx = 0; idx < content.size(); idx++) {
            mapperContent.add(toResponse(content.get(idx)));
        }
        companyPageResponse.setContent(mapperContent);
        companyPageResponse.setTotalPages(companyOfPage.getTotalPages());
        companyPageResponse.setHashNext(companyOfPage.hasNext());
        companyPageResponse.setHasPrevious(companyOfPage.hasPrevious());
        companyPageResponse.setHasContent(companyOfPage.hasContent());
        companyPageResponse.setTotalElements(companyOfPage.getTotalElements());
        return companyPageResponse;
    }

    public static Company toRequest(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }

    public static Company toUpdate(Company companyRequest,Company companyFromDb) {
        companyFromDb.setCompanyName(companyRequest.getCompanyName());
        return companyFromDb;
    }
}
