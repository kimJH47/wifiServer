package com.core.wifiserver.exception;

public class ClientNotWorkingException extends RuntimeException {
    private static final String MESSAGE = "클라이언트 서비스가 동작하지 않습니다.";

    public ClientNotWorkingException() {
        super(MESSAGE);
    }

    public ClientNotWorkingException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
