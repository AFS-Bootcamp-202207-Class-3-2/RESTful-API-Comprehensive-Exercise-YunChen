package com.rest.springbootemployee.exception;

public class EmployeeNotFindException extends RuntimeException {
    public EmployeeNotFindException() {
        super("not find query employee.");
    }
}
