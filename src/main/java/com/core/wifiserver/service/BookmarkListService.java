package com.core.wifiserver.service;

import com.core.wifiserver.dao.BookmarkListDao;
import com.core.wifiserver.dto.BookmarkListDto;
import com.core.wifiserver.dto.request.BookmarkSaveRequest;
import com.core.wifiserver.dto.request.Request;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BookmarkListService {
    private final BookmarkListDao bookmarkListDao;

    public Integer save(Request<BookmarkSaveRequest> request) {
        return bookmarkListDao.save(request.getEntity().getWifiName(),
                request.getEntity().getBookmarkGroupId());
    }

    public Integer delete(int id) {
        return bookmarkListDao.delete(id);
    }

    public List<BookmarkListDto> findAll() {
        return bookmarkListDao.findAll();
    }

}
