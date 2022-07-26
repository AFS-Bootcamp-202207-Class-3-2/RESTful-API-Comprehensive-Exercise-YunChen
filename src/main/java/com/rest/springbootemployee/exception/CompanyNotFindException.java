package com.rest.springbootemployee.exception;

import com.rest.springbootemployee.utils.Constant;

public class CompanyNotFindException extends RuntimeException {
    public CompanyNotFindException(){
        super(Constant.COMPANY_IS_NOT_EXIST);
    }
}
