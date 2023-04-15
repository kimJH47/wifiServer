package com.core.wifiserver.client.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WifiInfoDeserializer implements JsonDeserializer<ResponseEntity> {

    @Override
    public ResponseEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject responseBody = json.getAsJsonObject()
                .getAsJsonObject("TbPublicWifiInfo");
        return new ResponseEntity(
                responseBody.get("list_total_count").getAsInt(),
                responseBody.get("RESULT").getAsJsonObject().get("CODE").getAsString(),
                jsonArrayToDto(responseBody.get("row").getAsJsonArray())
        );
    }

    private List<WifiInfoDto> jsonArrayToDto(JsonArray jsonArray) {
        ArrayList<WifiInfoDto> wifiInfoDtos = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            wifiInfoDtos.add(WifiInfoDto.create(jsonElement.getAsJsonObject()));
        }
        return wifiInfoDtos;
    }
}
