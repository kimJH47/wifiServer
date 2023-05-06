package com.core.wifiserver.service;

import com.core.wifiserver.dao.HistoryDao;
import com.core.wifiserver.dto.HistoryDto;
import com.core.wifiserver.dto.request.HistorySaveRequest;
import java.util.List;

public class HistoryService {
    private final HistoryDao historyDao;

    public HistoryService(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    public Integer save(HistorySaveRequest request) {
        return historyDao.save(request.getLatitude(), request.getLongitude());
    }

    public Integer delete(int id) {
        return historyDao.delete(id);
    }

    public List<HistoryDto> findAll() {
        return historyDao.findAll();
    }
}