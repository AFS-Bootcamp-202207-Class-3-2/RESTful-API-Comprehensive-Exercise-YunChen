package com.rest.springbootemployee.utils;

import com.rest.springbootemployee.Dto.EmployeeRequest;
import com.rest.springbootemployee.Dto.EmployeeResponse;
import com.rest.springbootemployee.enities.Employee;
import org.springframework.beans.BeanUtils;

public class EmployeeMapper {
    public static EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }
    public static EmployeeRequest toEntity(Employee employeeRequest) {
        EmployeeRequest employeeResponse = new EmployeeRequest();
        BeanUtils.copyProperties(employeeRequest, employeeResponse);
        return employeeResponse;
    }
}
