package com.core.wifiserver.exception;

public class DatabaseInoperativeException extends RuntimeException {
    private static final String MESSAGE = "데이터베이스에서 오류가 발생 했습니다.";

    public DatabaseInoperativeException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
