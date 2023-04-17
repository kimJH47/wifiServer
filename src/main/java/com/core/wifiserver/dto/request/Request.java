package com.core.wifiserver.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Request<T> {
    private T entity;
}
