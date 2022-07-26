package com.rest.springbootemployee.mapper;


import com.rest.springbootemployee.enities.Company;
import com.rest.springbootemployee.enities.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Component
public class CompaniesRepository implements InitializingBean {
    private List<Company> companies = new ArrayList<>();

    private String nextId;
    @Override
    public void afterPropertiesSet() throws Exception {
        companies.addAll(
                Arrays.asList(
                        new Company("1", "spring", Arrays.asList(
                                new Employee("1", "Mike", 18, "male", 9000),
                                new Employee("2", "John", 16, "male", 6000),
                                new Employee("3", "Sarah", 22, "Female", 12000)
                        )),new Company("2", "oocl", Arrays.asList(
                                new Employee("1", "czy", 18, "male", 99999999),
                                new Employee("2", "John", 16, "male", 66666),
                                new Employee("3", "Sarah", 22, "Female", 127600)
                        ))
                )
        );
        nextId = String.valueOf(companies.size() + 1);
    }

}
