package com.core.wifiserver.dao;

import com.core.wifiserver.client.dto.WifiInfoDto;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class WifiInfoDao {

    private static final String TABLE_NAME = "PUBLIC_WIFI_INFO";
    private static final int MAX_BATCH_CONTENT = 1000;

    public long save(List<WifiInfoDto> publicWifiList) {
        Connection connection = ConnectionProvider.getConnection();
        List<String> queries = publicWifiList.stream()
                .map(this::createInsertQuery)
                .collect(Collectors.toList());
        //work batch
        try (Statement statement = connection.createStatement()) {
            for (int i = 0; i < queries.size(); i++) {
                String query = queries.get(i);
                statement.addBatch(query);
                if (i % MAX_BATCH_CONTENT == 0) {
                    statement.executeBatch();
                }
            }
            return statement.executeBatch().length;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            ConnectionProvider.close(connection);
        }
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