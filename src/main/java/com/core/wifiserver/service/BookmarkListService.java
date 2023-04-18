package com.core.wifiserver.service;

import com.core.wifiserver.dao.BookmarkListDao;
import com.core.wifiserver.dto.BookmarkListDto;
import com.core.wifiserver.dto.StatusCode;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.response.Response;

public class BookmarkListService {
    private final BookmarkListDao bookmarkListDao;

    public BookmarkListService() {
        bookmarkListDao = new BookmarkListDao();
    }

    public Response<Integer> save(Request<BookmarkListDto> request) {
        BookmarkListDto entity = request.getEntity();
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(bookmarkListDao.save(entity.getBookMarkGroupName(),
                        entity.getWifiName(),
                        entity.getId()
                ))
                .build();
    }
}
