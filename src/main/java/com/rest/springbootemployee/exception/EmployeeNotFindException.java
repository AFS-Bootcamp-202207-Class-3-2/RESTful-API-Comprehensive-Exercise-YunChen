package com.rest.springbootemployee.exception;

public class EmployeeNotFindException extends Exception {
    public EmployeeNotFindException() {
        super("not find query employee.");
    }
}
