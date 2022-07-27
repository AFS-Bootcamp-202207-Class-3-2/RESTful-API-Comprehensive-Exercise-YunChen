package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFindException;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    public List<Employee> findALl() {
        return employeeRepository.getAllEmployees();
    }

    public Boolean update(String id, Employee employeeToUpdate) {
        try {
            Employee employeeFromDb = employeeRepository.queryEmployeeById(id);
            if (employeeToUpdate.getSalary() != null) {
                employeeFromDb.setSalary(employeeToUpdate.getSalary() );
            }
            return true;
        }catch (EmployeeNotFindException exception) {
            return false;
        }
    }

    public List<Employee> queryEmployeeByGender(String male) {
        return employeeRepository.queryByGender(male);
    }

    public Employee queryEmployeeById(String employeeId) {
        return employeeRepository.queryEmployeeById(employeeId);
    }

    public List<Employee> findEmployeeByPage(int page, int pageSize) {
        return employeeRepository.findByPage(page, pageSize);
    }

    public Boolean insertEmployee(Employee insertEmployee) {
        return employeeRepository.insert(insertEmployee);
    }

    public Boolean removeEmployee(String removeEmployeeId) {
        return employeeRepository.deleteEmployee(removeEmployeeId);
    }
}
