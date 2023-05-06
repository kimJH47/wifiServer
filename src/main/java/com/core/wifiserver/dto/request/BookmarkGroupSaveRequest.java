package com.core.wifiserver.dto.request;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkGroupSaveRequest {
    private final String name;
    private final int orders;

    public static BookmarkGroupSaveRequest create(JsonObject jsonObject) {
        return new BookmarkGroupSaveRequest(jsonObject.get("name").getAsString(), jsonObject.get("orders").getAsInt());
    }

}
