package com.core.wifiserver.service;


import com.core.wifiserver.client.PublicApiResponseClient;
import com.core.wifiserver.client.dto.PublicApiConfig;
import com.core.wifiserver.dao.WifiInfoDao;
import com.core.wifiserver.dto.StatusCode;
import com.core.wifiserver.dto.response.Response;
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
            last+= maxResponseCount + 1;
        }
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(insertRow)
                .build();
    }
}
