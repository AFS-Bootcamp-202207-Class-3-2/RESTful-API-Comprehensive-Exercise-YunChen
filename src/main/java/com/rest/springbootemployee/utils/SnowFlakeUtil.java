package com.rest.springbootemployee.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class SnowFlakeUtil {
    private static Snowflake snowflake;
    public static  Snowflake getSnowFlake() {
        if (snowflake == null) {
            synchronized (SnowFlakeUtil.class) {
                if (snowflake == null) {
                    snowflake = IdUtil.createSnowflake(0,0);
                }
            }
        }
        return snowflake;
    }
}
