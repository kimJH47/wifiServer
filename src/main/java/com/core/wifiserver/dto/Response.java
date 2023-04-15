package com.core.wifiserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Response<T> {
    private final StatusCode statusCode;
    private final T entity;
}
