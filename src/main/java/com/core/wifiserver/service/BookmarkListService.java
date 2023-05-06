package com.core.wifiserver.service;

import com.core.wifiserver.dao.BookmarkListDao;
import com.core.wifiserver.dto.BookmarkListDto;
import com.core.wifiserver.dto.request.BookmarkSaveRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BookmarkListService {
    private final BookmarkListDao bookmarkListDao;

    public Integer save(BookmarkSaveRequest request) {
        return bookmarkListDao.save(request.getWifiName(), request.getBookmarkGroupId());
    }

    public Integer delete(int id) {
        return bookmarkListDao.delete(id);
    }

    public List<BookmarkListDto> findAll() {
        return bookmarkListDao.findAll();
    }

}
