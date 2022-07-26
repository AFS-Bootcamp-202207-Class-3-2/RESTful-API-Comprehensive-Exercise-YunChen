package com.rest.springbootemployee.enities;

import com.rest.springbootemployee.utils.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
    public String id;
    public Company() {
        id = SnowFlakeUtil.getSnowFlake().nextIdStr();
    }
}
