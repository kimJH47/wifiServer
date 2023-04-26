package com.core.wifiserver.service;

import com.core.wifiserver.dao.BookmarkListDao;
import com.core.wifiserver.dto.BookmarkListDto;
import com.core.wifiserver.dto.StatusCode;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.response.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BookmarkListService {
    private final BookmarkListDao bookmarkListDao;

    public Response<Integer> save(Request<BookmarkListDto> request) {
        BookmarkListDto entity = request.getEntity();
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(bookmarkListDao.save(entity.getWifiName(), entity.getId()
                ))
                .build();
    }

    public Response<Integer> delete(int id) {
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(bookmarkListDao.delete(id))
                .build();
    }

    public Response<List<BookmarkListDto>> findAll() {
        return Response.<List<BookmarkListDto>>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(bookmarkListDao.findAll())
                .build();
    }

}
