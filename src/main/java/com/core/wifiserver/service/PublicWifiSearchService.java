package com.core.wifiserver.service;


import com.core.wifiserver.client.SeoulPublicWifiClient;
import com.core.wifiserver.client.dto.PublicApiConfig;
import com.core.wifiserver.dao.WifiInfoDao;
import com.core.wifiserver.dto.StatusCode;
import com.core.wifiserver.dto.WifiDto;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.request.WifiSearchRequest;
import com.core.wifiserver.dto.response.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PublicWifiSearchService {
    private final SeoulPublicWifiClient client;
    private final WifiInfoDao wifiInfoDao;

    public Response<Integer> addPublicWifi() {
        int apiTotalCount = client.getApiTotalCount();
        int maxResponseCount = PublicApiConfig.getMaxResponseCount();
        int last = 0;
        int insertRow = 0;
        while (apiTotalCount > 0) {
            if (apiTotalCount < maxResponseCount) {
                insertRow += wifiInfoDao.save(client.getPublicWifiList(last, last + apiTotalCount)
                        .getWifiInfos());
                break;
            }
            insertRow += wifiInfoDao.save(client.getPublicWifiList(last, last + maxResponseCount)
                    .getWifiInfos());

            apiTotalCount -= maxResponseCount;
            last += maxResponseCount + 1;
        }
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(insertRow)
                .build();
    }

    public Response<List<WifiDto>> findOrderByCoordinateWithPagination(Request<WifiSearchRequest> request) {
        return Response.<List<WifiDto>>builder()
                .statusCode(StatusCode.SUCCESS)
                .totalCount(geTotalCount())
                .entity(wifiInfoDao.findOrderByCoordinateWithPagination(
                        request.getEntity().getLatitude(),
                        request.getEntity().getLongitude(),
                        request.getEntity().getPage()))
                .build();
    }

    public Response<WifiDto> findOne(String mgrNo, double latitude, double longitude) {
        return Response.<WifiDto>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(wifiInfoDao.findOne(mgrNo, latitude, longitude))
                .build();
    }

    private int geTotalCount() {
        return wifiInfoDao.getTotalCount();
    }
}
