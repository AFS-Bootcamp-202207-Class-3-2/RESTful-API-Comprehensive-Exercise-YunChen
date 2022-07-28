package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    @Autowired
    private EmployeeDao employeeDao;

    public Employee insertEmployee(Employee employee) {
        return employeeDao.save(employee);
    }
}
