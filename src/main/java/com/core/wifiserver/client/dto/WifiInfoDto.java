package com.core.wifiserver.client.dto;


import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class WifiInfoDto {
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

    public static WifiInfoDto create(JsonObject jsonObject) {
        return WifiInfoDto.builder().
                mgrNo(jsonObject.get("X_SWIFI_MGR_NO").getAsString())
                .WRDOFC(jsonObject.get("X_SWIFI_WRDOFC").getAsString())
                .name(jsonObject.get("X_SWIFI_MAIN_NM").getAsString())
                .streetAddress(jsonObject.get("X_SWIFI_ADRES1").getAsString())
                .detailAddress(jsonObject.get("X_SWIFI_ADRES2").getAsString())
                .installFloor(jsonObject.get("X_SWIFI_INSTL_FLOOR").getAsString())
                .installType(jsonObject.get("X_SWIFI_INSTL_TY").getAsString())
                .installMby(jsonObject.get("X_SWIFI_INSTL_MBY").getAsString())
                .svcEc(jsonObject.get("X_SWIFI_SVC_SE").getAsString())
                .cmcwr(jsonObject.get("X_SWIFI_CMCWR").getAsString())
                .cnstcYear(jsonObject.get("X_SWIFI_CNSTC_YEAR").getAsString())
                .inoutDoor(jsonObject.get("X_SWIFI_INOUT_DOOR").getAsString())
                .remars3(jsonObject.get("X_SWIFI_REMARS3").getAsString())
                .latitude(jsonObject.get("LAT").getAsString())
                .longitude(jsonObject.get("LNT").getAsString())
                .workDttm(jsonObject.get("WORK_DTTM").getAsString())
                .build();
    }
}
