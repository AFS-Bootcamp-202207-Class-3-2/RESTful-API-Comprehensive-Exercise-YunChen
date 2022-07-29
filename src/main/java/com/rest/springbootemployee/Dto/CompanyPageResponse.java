package com.rest.springbootemployee.Dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyPageResponse {
    private List<CompanyResponse> content = new ArrayList<>();
    private int totalPages;
    private long totalElements;
    private boolean last;
    private boolean hashNext;
    private boolean hasPrevious;
    private boolean hasContent;
}
