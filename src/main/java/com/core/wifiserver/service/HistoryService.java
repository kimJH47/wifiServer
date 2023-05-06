package com.core.wifiserver.service;

import com.core.wifiserver.dao.HistoryDao;
import com.core.wifiserver.dto.HistoryDto;
import com.core.wifiserver.dto.request.HistoryRequest;
import com.core.wifiserver.dto.request.Request;
import java.util.List;

public class HistoryService {
    private final HistoryDao historyDao;

    public HistoryService(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    public Integer save(Request<HistoryRequest> historyRequestRequest) {
        return historyDao.save(historyRequestRequest.getEntity().getLatitude(),
                historyRequestRequest.getEntity().getLongitude());

    }

    public Integer delete(int id) {
        return historyDao.delete(id);
    }

    public List<HistoryDto> findAll() {
        return historyDao.findAll();
    }
}