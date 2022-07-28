package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.enities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDao extends JpaRepository<Company,String> {
}
