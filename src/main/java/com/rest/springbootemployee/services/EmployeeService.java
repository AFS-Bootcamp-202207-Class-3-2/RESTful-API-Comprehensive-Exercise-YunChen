package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFindException;
import com.rest.springbootemployee.mapper.EmployeeDao;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeDao employeeDao;
    public List<Employee> findALl() {
        return employeeDao.findAll();
    }

    public Employee update(String id, Employee employeeToUpdate) {
        Employee employee = employeeDao.findById(id).orElseThrow(EmployeeNotFindException::new);
        if (employee != null) {
            employeeToUpdate.setId(employee.getId());
            employeeDao.save(employeeToUpdate);
        }
        return employee;
    }

    public List<Employee> queryEmployeeByGender(String gender) {
        return employeeDao.findByGender(gender);
    }

    public Employee queryEmployeeById(String employeeId) {
        return employeeDao.findById(employeeId).orElseThrow(EmployeeNotFindException::new);
    }

    public List<Employee> findEmployeeByPage(int page, int pageSize) {
        return employeeDao.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Employee insertEmployee(Employee insertEmployee) {
        return employeeDao.save(insertEmployee);
    }

    public Employee removeEmployee(String removeEmployeeId) {
        Employee employee = employeeDao.findById(removeEmployeeId).orElseThrow(EmployeeNotFindException::new);
        employeeDao.delete(employee);
        return employee;
    }
}
