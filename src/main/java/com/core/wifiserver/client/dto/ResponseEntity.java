package com.core.wifiserver.client.dto;

import com.google.gson.annotations.JsonAdapter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonAdapter(WifiInfoDeserializer.class)
public class ResponseEntity {
    private Integer totalCount;
    private String resultCode;
    private List<clientResponseDto> wifiInfos;
}
