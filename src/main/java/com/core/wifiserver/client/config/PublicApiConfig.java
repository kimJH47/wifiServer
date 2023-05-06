package com.core.wifiserver.client.config;

public class PublicApiConfig {

    private static final int MAX_RESPONSE_COUNT = 999;
    private static final String API_KEY = "4874466375737731353377496c456f";
    private static final String BASE_URI = "http://openapi.seoul.go.kr:8088";

    private PublicApiConfig() {

    }

    public static int getMaxResponseCount() {
        return MAX_RESPONSE_COUNT;
    }

    public static String createURI(String responseType, String url, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("시작값이 더작아야함");
        }
        if (start == end) {
            throw new IllegalArgumentException("최소 1건 이상의 요청크기");
        }
        if (end - start > MAX_RESPONSE_COUNT) {
            throw new IllegalArgumentException("최대 요청 크기 초과");
        }
        return BASE_URI + "/"
                + API_KEY + "/"
                + responseType + "/"
                + url + "/"
                + start + "/"
                + end;
    }

}
