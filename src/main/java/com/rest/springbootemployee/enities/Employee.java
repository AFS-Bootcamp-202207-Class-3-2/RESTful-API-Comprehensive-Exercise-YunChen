package com.rest.springbootemployee.enities;

import com.rest.springbootemployee.utils.SnowFlakeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee {
    private String id;
    private String name;

    public Employee() {
        id = SnowFlakeUtil.getSnowFlake().nextIdStr();
    }

    private int age;
    private String gender;
    private int salary;
}
