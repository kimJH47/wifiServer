package com.core.wifiserver.dto.request;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkGroupUpdateRequest {
    private final int id;
    private final String name;
    private final int orders;

    public static BookmarkGroupUpdateRequest create(JsonObject jsonObject) {
        return new BookmarkGroupUpdateRequest(
                jsonObject.get("id").getAsInt(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("orders").getAsInt());
    }
}

