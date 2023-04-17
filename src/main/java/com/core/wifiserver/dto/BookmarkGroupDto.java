package com.core.wifiserver.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookmarkGroupDto {

    private final int id;
    private final String name;
    private final String createDate;
    private final String modifiedDate;
}
