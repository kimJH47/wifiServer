package com.core.wifiserver.service;

import com.core.wifiserver.dao.HistoryDao;
import com.core.wifiserver.dto.HistoryDto;
import com.core.wifiserver.dto.StatusCode;
import com.core.wifiserver.dto.request.HistoryRequest;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.response.Response;
import java.util.List;

public class HistoryService {
    private final HistoryDao historyDao;

    public HistoryService(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    public Response<?> save(Request<HistoryRequest> historyRequestRequest) {
        return Response.builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(historyDao.save(historyRequestRequest.getEntity().getLatitude(),
                        historyRequestRequest.getEntity().getLongitude()))
                .build();

    }

    public Response<Integer> delete(int id) {
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(historyDao.delete(id))
                .build();
    }

    public Response<List<HistoryDto>> findAll() {
        return Response.<List<HistoryDto>>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(historyDao.findAll())
                .build();
    }
}