package com.core.wifiserver.dao;

import com.core.wifiserver.client.dto.WifiInfoDto;
import com.core.wifiserver.dao.queryfactory.QueryBuilderFactory;
import com.core.wifiserver.dao.template.JdbcContext;
import java.util.List;
import java.util.stream.Collectors;

public class WifiInfoDao {

    private static final String TABLE_NAME = "PUBLIC_WIFI_INFO";
    private final JdbcContext jdbcContext;

    public WifiInfoDao() {
        this.jdbcContext = new JdbcContext();
    }

    public long save(List<WifiInfoDto> publicWifiList) {
        //work batch
        return jdbcContext.insertBulk(publicWifiList.stream()
                .map(this::createInsertQuery)
                .collect(Collectors.toList()));
    }

    //dto 로 넘길까 고민
    private String createInsertQuery(WifiInfoDto wifiInfoDto) {
        return QueryBuilderFactory.createInsertQueryBuilder(TABLE_NAME)
                .addColumn("MGR_NO").value(wifiInfoDto.getMgrNo())
                .addColumn("WRDOFC").value(wifiInfoDto.getWRDOFC())
                .addColumn("NAME").value(wifiInfoDto.getName())
                .addColumn("STREET_ADDRESS").value(wifiInfoDto.getStreetAddress())
                .addColumn("DETAIL_ADDRESS").value(wifiInfoDto.getDetailAddress())
                .addColumn("INSTALL_FLOOR").value(wifiInfoDto.getInstallFloor())
                .addColumn("INSTALL_TYPE").value(wifiInfoDto.getInstallType())
                .addColumn("INSTALL_MBY").value(wifiInfoDto.getInstallMby())
                .addColumn("SVC_SE").value(wifiInfoDto.getSvcEc())
                .addColumn("CMCWR").value(wifiInfoDto.getCmcwr())
                .addColumn("CNSTC_YEAR").value(wifiInfoDto.getCnstcYear())
                .addColumn("INOUT_DOOR").value(wifiInfoDto.getInoutDoor())
                .addColumn("REMARS3").value(wifiInfoDto.getRemars3())
                .addColumn("LAT").value(wifiInfoDto.getLatitude())
                .addColumn("LNT").value(wifiInfoDto.getLongitude())
                .addColumn("WORK_DTTM").value(wifiInfoDto.getWorkDttm())
                .build();
    }
}