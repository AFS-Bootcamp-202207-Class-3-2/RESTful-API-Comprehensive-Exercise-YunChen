package com.rest.springbootemployee.Dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> content = new ArrayList<>();
    private int totalPages;
    private long totalElements;
    private boolean last;
    private boolean hashNext;
    private boolean hasPrevious;
    private boolean hasContent;
}
