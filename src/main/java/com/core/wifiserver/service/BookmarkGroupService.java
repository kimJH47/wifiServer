package com.core.wifiserver.service;

import com.core.wifiserver.dao.BookmarkGroupDao;
import com.core.wifiserver.dto.BookmarkGroupDto;
import com.core.wifiserver.dto.StatusCode;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.response.Response;

public class BookmarkGroupService {
    private final BookmarkGroupDao bookmarkGroupDao;

    public BookmarkGroupService() {
        this.bookmarkGroupDao = new BookmarkGroupDao();
    }

    public Response<Integer> save(Request<BookmarkGroupDto> bookmarkGroupRequest) {
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(bookmarkGroupDao.save(bookmarkGroupRequest.getEntity().getName(),
                        bookmarkGroupRequest.getEntity().getOrder()))
                .build();

    }
}
