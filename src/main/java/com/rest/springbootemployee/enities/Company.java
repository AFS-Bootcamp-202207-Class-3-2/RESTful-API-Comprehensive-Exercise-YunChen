package com.rest.springbootemployee.enities;

import com.rest.springbootemployee.utils.SnowFlakeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Company {
    public String id;
    public Company() {
        id = SnowFlakeUtil.getSnowFlake().nextIdStr();
    }

    private String companyName;
    private List<Employee> employees = new ArrayList<>();
}
