package com.core.wifiserver.dao;

import com.core.wifiserver.client.dto.clientResponseDto;
import com.core.wifiserver.dao.queryfactory.QueryBuilderFactory;
import com.core.wifiserver.dao.template.JdbcContext;
import com.core.wifiserver.dto.Page;
import com.core.wifiserver.dto.WifiDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WifiInfoDao {

    private static final String TABLE_NAME = "PUBLIC_WIFI_INFO";
    private static final String NO_SEARCH_WIFI = "와이파이 정보가 존재하지 않습니다.";
    private final JdbcContext jdbcContext;

    public WifiInfoDao() {
        this.jdbcContext = JdbcContext.getInstance();
    }

    public long save(List<clientResponseDto> publicWifiList) {
        //work batch
        return jdbcContext.insertBulk(publicWifiList.stream()
                .map(this::createInsertQuery)
                .collect(Collectors.toList()));
    }

    //dto 로 넘길까 고민
    private String createInsertQuery(clientResponseDto clientResponseDto) {
        return QueryBuilderFactory.createInsertQueryBuilder(TABLE_NAME)
                .addColumn("MGR_NO").value(clientResponseDto.getMgrNo())
                .addColumn("WRDOFC").value(clientResponseDto.getWRDOFC())
                .addColumn("NAME").value(clientResponseDto.getName())
                .addColumn("STREET_ADDRESS").value(clientResponseDto.getStreetAddress())
                .addColumn("DETAIL_ADDRESS").value(clientResponseDto.getDetailAddress())
                .addColumn("INSTALL_FLOOR").value(clientResponseDto.getInstallFloor())
                .addColumn("INSTALL_TYPE").value(clientResponseDto.getInstallType())
                .addColumn("INSTALL_MBY").value(clientResponseDto.getInstallMby())
                .addColumn("SVC_SE").value(clientResponseDto.getSvcEc())
                .addColumn("CMCWR").value(clientResponseDto.getCmcwr())
                .addColumn("CNSTC_YEAR").value(clientResponseDto.getCnstcYear())
                .addColumn("INOUT_DOOR").value(clientResponseDto.getInoutDoor())
                .addColumn("REMARS3").value(clientResponseDto.getRemars3())
                .addColumn("LAT").value(clientResponseDto.getLatitude())
                .addColumn("LNT").value(clientResponseDto.getLongitude())
                .addColumn("WORK_DTTM").value(clientResponseDto.getWorkDttm())
                .build();
    }


    public List<WifiDto> findOrderByCoordinateWithPagination(double latitude, double longitude, Page page) {
        return jdbcContext.select(createOrderByCoordinateWithPaginationQuery(latitude, longitude, page), resultSet -> {
            ArrayList<WifiDto> wifiResponseDtos = new ArrayList<>();
            while (resultSet.next()) {
                wifiResponseDtos.add(getWifiInfoDto(resultSet));
            }
            return wifiResponseDtos;
        });
    }

    private String createOrderByCoordinateWithPaginationQuery(double latitude, double longitude, Page page) {
        String distance = String.format(
                "(ABS(LAT - %f) * ABS(LAT - %f) + ABS(LNT - %f) * ABS(LNT - %f)) as DISTANCE",
                latitude, latitude,
                longitude, longitude);
        return QueryBuilderFactory.createSelectQueryBuilder(TABLE_NAME)
                .columns("*", distance)
                .orderBy("DISTANCE")
                .page(page.getPageSize(), page.getOffset())
                .build();
    }

    public WifiDto findOne(String mgrNo, double latitude, double longitude) {
        String distance = String.format(
                "(ABS(LAT - %f) * ABS(LAT - %f) + ABS(LNT - %f) * ABS(LNT - %f)) as DISTANCE",
                latitude, latitude,
                longitude, longitude);
        String query = QueryBuilderFactory.createSelectQueryBuilder(TABLE_NAME)
                .columns("*", distance)
                .where(String.format("MGR_NO = '%s'", mgrNo))
                .build();
        return jdbcContext.select(query, resultSet -> {
            try {
                return getWifiInfoDto(resultSet);
            } catch (Exception e) {
                throw new IllegalArgumentException(NO_SEARCH_WIFI);
            }
        });
    }

    private WifiDto getWifiInfoDto(ResultSet resultSet) throws SQLException {
        return WifiDto.builder()
                .distance(Math.round(resultSet.getDouble("DISTANCE") * 100) / 100.0)
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

    public int getTotalCount() {
        String query = QueryBuilderFactory.createSelectQueryBuilder(TABLE_NAME)
                .columns("count(*) as count")
                .build();
        return jdbcContext.select(query, resultSet -> resultSet.getInt("count"));
    }
}