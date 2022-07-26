package com.rest.springbootemployee.exception;

import com.rest.springbootemployee.utils.Constant;

public class EmployeeNotFindException extends RuntimeException {
    public EmployeeNotFindException() {
        super(Constant.EMPLOYEE_IS_NOT_EXIST);
    }
}
