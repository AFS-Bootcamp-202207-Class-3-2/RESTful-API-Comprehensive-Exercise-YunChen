package com.rest.springbootemployee.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class EmployeeResponse {
    private String id;
    private String name;
    private int age;
    private String gender;
}
