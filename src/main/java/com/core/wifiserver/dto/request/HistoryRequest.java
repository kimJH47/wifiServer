package com.core.wifiserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HistoryRequest {
    private final double latitude;
    private final double longitude;
}
