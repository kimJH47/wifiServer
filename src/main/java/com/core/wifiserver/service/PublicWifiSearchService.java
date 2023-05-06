package com.core.wifiserver.service;


import com.core.wifiserver.client.SeoulPublicWifiClient;
import com.core.wifiserver.client.config.PublicApiConfig;
import com.core.wifiserver.dao.WifiInfoDao;
import com.core.wifiserver.dto.WifiDto;
import com.core.wifiserver.dto.request.WifiSearchRequest;
import com.core.wifiserver.dto.response.WifiPaginationEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PublicWifiSearchService {
    private final SeoulPublicWifiClient client;
    private final WifiInfoDao wifiInfoDao;

    public Integer addPublicWifi() {
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
        return insertRow;
    }

    public WifiPaginationEntity findOrderByCoordinateWithPagination(WifiSearchRequest request) {
        return new WifiPaginationEntity(geTotalCount(),
                wifiInfoDao.findOrderByCoordinateWithPagination(
                        request.getLatitude(),
                        request.getLongitude(),
                        request.getPage()));
    }

    public WifiDto findOne(String mgrNo, double latitude, double longitude) {
        return wifiInfoDao.findOne(mgrNo, latitude, longitude);
    }

    private int geTotalCount() {
        return wifiInfoDao.getTotalCount();
    }
}
