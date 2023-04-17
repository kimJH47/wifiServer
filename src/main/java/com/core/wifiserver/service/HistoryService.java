package com.core.wifiserver.service;

import com.core.wifiserver.dao.HistoryDao;
import com.core.wifiserver.dto.request.HistoryRequest;
import com.core.wifiserver.dto.request.Request;

public class HistoryService {
    private final HistoryDao historyDao;

    public HistoryService(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    public int save(Request<HistoryRequest> historyRequestRequest) {
        return historyDao.save(historyRequestRequest.getEntity().getLatitude(),
                historyRequestRequest.getEntity().getLongitude());
    }

    public int delete(int id) {
        return historyDao.delete(id);
    }


}