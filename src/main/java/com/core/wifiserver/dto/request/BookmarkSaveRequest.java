package com.core.wifiserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkSaveRequest {
    private final int bookmarkGroupId;
    private final String wifiName;
}
