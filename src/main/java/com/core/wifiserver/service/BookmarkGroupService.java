package com.core.wifiserver.service;

import com.core.wifiserver.dao.BookmarkGroupDao;
import com.core.wifiserver.dto.BookmarkGroupDto;
import com.core.wifiserver.dto.request.BookmarkGroupSaveRequest;
import com.core.wifiserver.dto.request.BookmarkGroupUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarkGroupService {
    private final BookmarkGroupDao bookmarkGroupDao;

    public Integer save(BookmarkGroupSaveRequest request) {
        return bookmarkGroupDao.save(request.getName(), request.getOrders());

    }

    public List<BookmarkGroupDto> findAll() {
        return bookmarkGroupDao.findAll();
    }

    public Integer delete(int id) {
        return bookmarkGroupDao.delete(id);
    }

    public Integer update(BookmarkGroupUpdateRequest bookmarkGroupRequest) {
        return bookmarkGroupDao.update(bookmarkGroupRequest.getId(),
                bookmarkGroupRequest.getOrders(),
                bookmarkGroupRequest.getName());
    }

}
