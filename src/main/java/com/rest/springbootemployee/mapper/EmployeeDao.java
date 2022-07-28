package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.enities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeDao extends JpaRepository<Employee,String> {

}
