package com.core.wifiserver.dto.response;

import com.core.wifiserver.dto.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Response<T> {
    private final StatusCode statusCode;
    private int totalCount;
    private final T entity;
}
