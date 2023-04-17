package com.core.wifiserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HistoryDto {

    private final int id;
    private final double latitude;
    private final double longitude;
    private final String createDate;

}
