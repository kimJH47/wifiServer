package com.core.wifiserver.service;


import com.core.wifiserver.client.PublicApiResponseClient;
import com.core.wifiserver.client.dto.PublicApiConfig;
import com.core.wifiserver.client.dto.WifiInfoDto;
import com.core.wifiserver.dao.WifiInfoDao;
import com.core.wifiserver.dto.StatusCode;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.request.WifiSearchRequest;
import com.core.wifiserver.dto.response.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PublicWifiSearchService {
    private final PublicApiResponseClient client;
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

            apiTotalCount -= maxResponseCount + 1;
            last += maxResponseCount + 1;
        }
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(insertRow)
                .build();
    }

    public Response<List<WifiInfoDto>> findOrderByCoordinateWithPagination(Request<WifiSearchRequest> request) {
        return Response.<List<WifiInfoDto>>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(wifiInfoDao.findOrderByCoordinateWithPagination(
                        request.getEntity().getLatitude(),
                        request.getEntity().getLongitude(),
                        request.getEntity().getPage()))
                .build();
    }

    public Response<WifiInfoDto> findOne(int id) {
        return Response.<WifiInfoDto>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(wifiInfoDao.findOne(id))
                .build();
    }
}
