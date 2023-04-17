package com.core.wifiserver.dao;

import com.core.wifiserver.client.dto.WifiInfoDto;
import com.core.wifiserver.dao.queryfactory.QueryBuilderFactory;
import com.core.wifiserver.dao.template.JdbcContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WifiInfoDao {

    private static final String TABLE_NAME = "PUBLIC_WIFI_INFO";
    private final JdbcContext jdbcContext;

    public WifiInfoDao() {
        this.jdbcContext = JdbcContext.getInstance();
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


    public List<WifiInfoDto> findOrderByCoordinateWithPagination(double latitude, double longitude, Page page) {
        return jdbcContext.select(createOrderByCoordinateWithPaginationQuery(latitude, longitude, page), resultSet -> {
            ArrayList<WifiInfoDto> wifiInfoDtos = new ArrayList<>();
            while (resultSet.next()) {
                wifiInfoDtos.add(getWifiInfoDto(resultSet));
            }
            return wifiInfoDtos;
        });
    }

    private String createOrderByCoordinateWithPaginationQuery(double latitude, double longitude, Page page) {
        String orderBy = String.format(
                "ABS(LAT - %f) * ABS(LAT - %f) + ABS(LNT - %f) * ABS(LNT - %f)",
                latitude, latitude,
                longitude, longitude);
        return QueryBuilderFactory.createSelectQueryBuilder(TABLE_NAME)
                .orderBy(orderBy)
                .page(page.getPageSize(), page.getOffset())
                .build();
    }

    public WifiInfoDto findOne(int id) {
        String query = QueryBuilderFactory.createSelectQueryBuilder(TABLE_NAME)
                .where(String.format("id = %d", id))
                .build();
        return jdbcContext.select(query, this::getWifiInfoDto);
    }

    private WifiInfoDto getWifiInfoDto(ResultSet resultSet) throws SQLException {
        return WifiInfoDto.builder()
                .mgrNo(resultSet.getString("MGR_NO"))
                .WRDOFC(resultSet.getString("WRDOFC"))
                .name(resultSet.getString("NAME"))
                .streetAddress(resultSet.getString("STREET_ADDRESS"))
                .detailAddress(resultSet.getString("DETAIL_ADDRESS"))
                .installFloor(resultSet.getString("INSTALL_FLOOR"))
                .installType(resultSet.getString("INSTALL_TYPE"))
                .installMby(resultSet.getString("INSTALL_MBY"))
                .svcEc(resultSet.getString("SVC_SE"))
                .cmcwr(resultSet.getString("CMCWR"))
                .cnstcYear(resultSet.getString("CNSTC_YEAR"))
                .inoutDoor(resultSet.getString("INOUT_DOOR"))
                .remars3(resultSet.getString("REMARS3"))
                .latitude(resultSet.getString("LAT"))
                .longitude(resultSet.getString("LNT"))
                .workDttm(resultSet.getString("WORK_DTTM"))
                .build();
    }
}