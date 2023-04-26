package com.core.wifiserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkListDto {
    private int id;
    private String bookmarkGroupName;
    private String wifiName;
    private String createDate;

}
