package com.core.wifiserver.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class WifiDto {
    private double distance;
    private String mgrNo;
    private String WRDOFC;
    private String name;
    private String streetAddress;
    private String detailAddress;
    private String installFloor;
    private String installType;
    private String installMby;
    private String svcEc;
    private String cmcwr;
    private String cnstcYear;
    private String inoutDoor;
    private String remars3;
    private String latitude; //위도
    private String longitude; //경도
    private String workDttm;
}
