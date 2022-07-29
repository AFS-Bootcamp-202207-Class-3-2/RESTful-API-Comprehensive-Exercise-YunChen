package com.rest.springbootemployee.utils;

import com.rest.springbootemployee.Dto.EmployeePageResponse;
import com.rest.springbootemployee.Dto.EmployeeRequest;
import com.rest.springbootemployee.Dto.EmployeeResponse;
import com.rest.springbootemployee.enities.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {
    public static EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }
    public static  EmployeePageResponse toResponse(Page<Employee> employeeOfPage) {
        EmployeePageResponse employeePageResponse = new EmployeePageResponse();
        List<Employee> content = employeeOfPage.getContent();
        List<EmployeeResponse> mapperContent = new ArrayList<>();
        for (int idx = 0; idx < content.size(); idx++) {
            mapperContent.add(toResponse(content.get(idx)));
        }
        employeePageResponse.setContent(mapperContent);
        employeePageResponse.setTotalPages(employeeOfPage.getTotalPages());
        employeePageResponse.setHashNext(employeeOfPage.hasNext());
        employeePageResponse.setHasPrevious(employeeOfPage.hasPrevious());
        employeePageResponse.setHasContent(employeeOfPage.hasContent());
        employeePageResponse.setTotalElements(employeeOfPage.getTotalElements());
        return employeePageResponse;
    }

    public static Employee toRequest(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }

    public static Employee toUpdate(Employee employeeRequest,Employee employeeFromDb) {
        employeeFromDb.setName(employeeRequest.getName());
        employeeFromDb.setAge(employeeRequest.getAge());
        employeeFromDb.setGender(employeeRequest.getGender());
        employeeFromDb.setSalary(employeeRequest.getSalary());
        employeeFromDb.setCompanyId(employeeRequest.getCompanyId());
        employeeFromDb.setCompanyName(employeeRequest.getCompanyName());
        return employeeFromDb;
    }
}
