package com.core.wifiserver.dto.request;

import com.core.wifiserver.dto.Page;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class WifiSearchRequest {
    private final Page page;
    private final double latitude;
    private final double longitude;
}
