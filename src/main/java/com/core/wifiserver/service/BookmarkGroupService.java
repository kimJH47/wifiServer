package com.core.wifiserver.service;

import com.core.wifiserver.dao.BookmarkGroupDao;
import com.core.wifiserver.dto.BookmarkGroupDto;
import com.core.wifiserver.dto.StatusCode;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.response.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarkGroupService {
    private final BookmarkGroupDao bookmarkGroupDao;

    public Response<Integer> save(Request<BookmarkGroupDto> bookmarkGroupRequest) {
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(bookmarkGroupDao.save(bookmarkGroupRequest.getEntity().getName(),
                        bookmarkGroupRequest.getEntity().getOrders()))
                .build();

    }

    public Response<List<BookmarkGroupDto>> findAll() {
        return Response.<List<BookmarkGroupDto>>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(bookmarkGroupDao.findAll())
                .build();
    }

    public Response<Integer> delete(int id) {
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(bookmarkGroupDao.delete(id))
                .build();
    }

    public Response<Integer> update(Request<BookmarkGroupDto> bookmarkGroupRequest) {
        BookmarkGroupDto entity = bookmarkGroupRequest.getEntity();
        return Response.<Integer>builder()
                .statusCode(StatusCode.SUCCESS)
                .entity(bookmarkGroupDao.update(entity.getId(),
                        entity.getOrders(),
                        entity.getName()))
                .build();
    }
}
