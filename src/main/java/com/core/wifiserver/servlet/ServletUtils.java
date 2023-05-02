package com.core.wifiserver.servlet;

import com.core.wifiserver.dto.StatusCode;
import com.core.wifiserver.dto.response.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {
    private ServletUtils() {

    }

    public static JsonObject createJsonObject(HttpServletRequest req) throws IOException {
        String requestBody = req.getReader()
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
        return new Gson().fromJson(requestBody, JsonObject.class);
    }

    public static <T> String entityToJson(T entity) {
        return new Gson().toJson(entity);
    }

    public static void createFailResponse(HttpServletResponse httpServletResponse, Exception e) throws IOException {
        httpServletResponse.getWriter().print(
                new Gson().toJson(Response.builder()
                        .statusCode(StatusCode.FAIL)
                        .entity(e.getMessage())
                        .build()));
    }

}