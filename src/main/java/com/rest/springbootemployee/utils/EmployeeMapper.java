package com.rest.springbootemployee.utils;

import com.rest.springbootemployee.Dto.EmployeePageResponse;
import com.rest.springbootemployee.Dto.EmployeeRequest;
import com.rest.springbootemployee.Dto.EmployeeResponse;
import com.rest.springbootemployee.enities.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class EmployeeMapper {
    public static EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }
    public static  EmployeePageResponse toResponse(Page<Employee> employeeOfPage) {
//        employeeOfPage = employeeOfPage;
        EmployeePageResponse employeePageResponse = new EmployeePageResponse();
        employeePageResponse.setContent(employeeOfPage.getContent());
        employeePageResponse.setTotalPages(employeeOfPage.getTotalPages());
        employeePageResponse.setHashNext(employeeOfPage.hasNext());
        employeePageResponse.setHasPrevious(employeeOfPage.hasPrevious());
        employeePageResponse.setHasContent(employeeOfPage.hasContent());
        employeePageResponse.setTotalElements(employeeOfPage.getTotalElements());
        return employeePageResponse;
    }
    public static EmployeeRequest toEntity(Employee employeeRequest) {
        EmployeeRequest employeeResponse = new EmployeeRequest();
        BeanUtils.copyProperties(employeeRequest, employeeResponse);
        return employeeResponse;
    }
}
