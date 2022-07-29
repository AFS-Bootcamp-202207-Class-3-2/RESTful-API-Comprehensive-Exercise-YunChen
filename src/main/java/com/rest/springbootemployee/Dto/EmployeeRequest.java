package com.rest.springbootemployee.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private String companyId;
    private String companyName;
}
