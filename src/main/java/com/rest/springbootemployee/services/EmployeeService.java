package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFindException;
import com.rest.springbootemployee.mapper.EmployeeDao;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            employeeDao.save(employee);
        }
        return employee;
    }

    public List<Employee> queryEmployeeByGender(String gender) {
        System.out.println(gender);
        return employeeDao.findByGender(gender);
    }

    public Employee queryEmployeeById(String employeeId) {
        return employeeRepository.queryEmployeeById(employeeId);
    }

    public List<Employee> findEmployeeByPage(int page, int pageSize) {
        return employeeRepository.findByPage(page, pageSize);
    }

    public Employee insertEmployee(Employee insertEmployee) {
        Employee saveEmployee = employeeDao.save(insertEmployee);
        insertEmployee.setId(saveEmployee.getId());
        return insertEmployee;
    }

    public Boolean removeEmployee(String removeEmployeeId) {
        return employeeRepository.deleteEmployee(removeEmployeeId);
    }
}
