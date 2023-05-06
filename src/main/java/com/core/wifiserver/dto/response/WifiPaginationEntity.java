package com.core.wifiserver.dto.response;

import com.core.wifiserver.dto.WifiDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WifiPaginationEntity {
    private final int totalCount;
    private final List<WifiDto> wifiList;
}
