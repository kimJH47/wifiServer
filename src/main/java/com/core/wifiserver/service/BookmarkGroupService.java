package com.core.wifiserver.service;

import com.core.wifiserver.dao.BookmarkGroupDao;
import com.core.wifiserver.dto.BookmarkGroupDto;
import com.core.wifiserver.dto.request.Request;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarkGroupService {
    private final BookmarkGroupDao bookmarkGroupDao;

    public Integer save(Request<BookmarkGroupDto> bookmarkGroupRequest) {
        return bookmarkGroupDao.save(bookmarkGroupRequest.getEntity().getName(),
                bookmarkGroupRequest.getEntity().getOrders());

    }

    public List<BookmarkGroupDto> findAll() {
        return bookmarkGroupDao.findAll();
    }

    public Integer delete(int id) {
        return bookmarkGroupDao.delete(id);
    }

    public Integer update(Request<BookmarkGroupDto> bookmarkGroupRequest) {
        return bookmarkGroupDao.update(bookmarkGroupRequest.getEntity().getId(),
                bookmarkGroupRequest.getEntity().getOrders(),
                bookmarkGroupRequest.getEntity().getName());
    }
}
