package com.rest.springbootemployee.services;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFindException;
import com.rest.springbootemployee.mapper.EmployeeDao;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import com.rest.springbootemployee.utils.EmployeeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        Employee employeeFromDb = employeeDao.findById(id).orElseThrow(EmployeeNotFindException::new);
        return employeeDao.save(EmployeeMapper.toUpdate(employeeToUpdate, employeeFromDb));
    }

    public List<Employee> queryEmployeeByGender(String gender) {
        return employeeDao.findByGender(gender);
    }

    public Employee queryEmployeeById(String employeeId) {
        return employeeDao.findById(employeeId).orElseThrow(EmployeeNotFindException::new);
    }

    public Page<Employee> findEmployeeByPage(int page, int pageSize) {
        if (page <= 0) {
            page = 0;
        }
        if (pageSize <= 1) {
            pageSize = 1;
        }
        return employeeDao.findAll(PageRequest.of(page , pageSize));
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
