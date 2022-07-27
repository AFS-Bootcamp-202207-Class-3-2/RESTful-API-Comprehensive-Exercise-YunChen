package com.rest.springbootemployee.services;

import com.rest.springbootemployee.mapper.CompaniesRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CompaniesServiceTest {
    @Mock
    CompaniesRepository companiesRepository;
    @InjectMocks
    CompaniesService companiesService;
}
