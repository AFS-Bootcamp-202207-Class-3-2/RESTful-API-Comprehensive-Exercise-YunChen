package com.rest.springbootemployee.utils;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class R {
    private R(){}

    public static void main(String[] args) {
        new R().setCode("200").setCode("asudgia").data("as", "as").setData(new HashMap<>());
    }
    private String code;
    private String msg;
    private Map<String, Object> data = new HashMap<>();
    public static R SUCCESS() {
        R r = new R();
        r.setCode("200");
        return r;
    }
    public static R SUCCESS(String message) {
        R r = new R();
        r.setCode("200");
        r.setMsg(message);
        return r;
    }

    public R data(String key, Object data) {
        this.data.put(key, data);
        return this;
    }

}
