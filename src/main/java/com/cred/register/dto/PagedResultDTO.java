package com.cred.register.dto;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class PagedResultDTO<T> {

    private final int page;
    private final int limit;
    private final long totalElements;
    private final long lastUpdate;
    private final List<T> elements;

    public PagedResultDTO(List<T> elements, long totalElements, int page, int limit) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.page = page;
        this.limit = limit;
        this.lastUpdate = new Date().getTime();
    }

    public boolean hasMore() {
        return totalElements > page + limit;
    }

    public boolean hasPrevious() {
        return page > 0 && totalElements > 0;
    }
}
