package com.core.wifiserver.servlet;

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

    public static <T> String entityToResponseJson(T entity) {
        return new Gson().toJson(Response.builder()
                .entity(entity)
                .build());
    }

    public static void createFailResponse(HttpServletResponse httpServletResponse, Exception e) throws IOException {
        httpServletResponse.setStatus(500);
        httpServletResponse.getWriter().print(
                new Gson().toJson(Response.builder()
                        .entity(e.getMessage())
                        .build()));
    }
}
