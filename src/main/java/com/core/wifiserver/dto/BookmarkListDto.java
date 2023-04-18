package com.core.wifiserver.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookmarkListDto {
    private final int id;
    private final String bookMarkGroupName;
    private final String wifiName;
    private final String createDate;


}
