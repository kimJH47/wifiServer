package com.core.wifiserver.client;

import com.core.wifiserver.client.config.PublicApiConfig;
import com.core.wifiserver.client.dto.ResponseEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SeoulPublicWifiClientTest {
    @Test
    @DisplayName("실제 요청테스트) 공공api 에 옮바른 형식의 url 을 get 요청을 보내면 ResponseEntity 에 파싱되어 반환되어야한다.")
    void get() throws Exception {
        //refactor -> used mock
        //given
        SeoulPublicWifiClient seoulPublicWifiClient = new SeoulPublicWifiClient();
        //when
        ResponseEntity responseEntity = seoulPublicWifiClient.getPublicWifiList(0,
                PublicApiConfig.getMaxResponseCount());
        //then
        String resultCode = responseEntity.getResultCode();
        Assertions.assertThat(resultCode).isEqualTo("INFO-000");
        Assertions.assertThat(responseEntity.getWifiInfos()).hasSize(PublicApiConfig.getMaxResponseCount());

    }
}