package com.core.wifiserver.client;


import com.core.wifiserver.client.config.PublicApiConfig;
import com.core.wifiserver.client.dto.ResponseEntity;
import com.core.wifiserver.exception.ClientNotWorkingException;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class SeoulPublicWifiClient {

    public ResponseEntity getPublicWifiList(int start, int end) {
        return getResponseEntity(start, end);
    }

    public int getApiTotalCount() {
        return getResponseEntity(0, 1).getTotalCount();
    }

    private ResponseEntity getResponseEntity(int start, int end) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request build = new Builder().url(createURI(start, end))
                    .get()
                    .build();
            Response execute = okHttpClient.newCall(build).execute();
            if (execute.isSuccessful()) {
                Gson gson = new Gson();
                return gson.fromJson(execute.body().string(), ResponseEntity.class);
            }
            throw new ClientNotWorkingException();
        } catch (Exception e) {
            throw new ClientNotWorkingException(e);
        }
    }

    private String createURI(int start, int end) {
        return PublicApiConfig.createURI("json", "TbPublicWifiInfo", start, end);
    }
}
