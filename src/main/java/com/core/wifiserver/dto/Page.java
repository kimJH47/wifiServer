package com.core.wifiserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Page {
    private int pageNumber;
    private int pageSize;

    public int getOffset() {
        return (pageNumber - 1) * pageSize;
    }
}
