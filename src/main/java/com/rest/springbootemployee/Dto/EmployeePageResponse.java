package com.rest.springbootemployee.Dto;

import com.rest.springbootemployee.enities.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeePageResponse {
    private List<Employee> content = new ArrayList<>();
    private int totalPages;
    private long totalElements;
    private boolean last;
    private boolean hashNext;
    private boolean hasPrevious;
    private boolean hasContent;
}
