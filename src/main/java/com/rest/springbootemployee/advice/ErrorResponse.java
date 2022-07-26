package com.rest.springbootemployee.advice;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private int errorCode;
    private String errorMessage;
}
