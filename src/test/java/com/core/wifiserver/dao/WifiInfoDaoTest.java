package com.core.wifiserver.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.core.wifiserver.client.dto.WifiInfoDto;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WifiInfoDaoTest {

    WifiInfoDao wifiInfoDao = mock(WifiInfoDao.class);

    @Test
    @DisplayName("PublicWifiDto 의 데이터를 PUBLIC_WIFI_INFO 테이블에 저장하여야한다.")
    void save() throws Exception {
        //given
        ArrayList<WifiInfoDto> wifiInfoDtos = new ArrayList<>();
        wifiInfoDtos.add(createDto());
        long expected = 1L;
        given(wifiInfoDao.save(any())).willReturn(expected);
        //when
        long actual = wifiInfoDao.save(wifiInfoDtos);
        //then
        then(wifiInfoDao).should(times(1)).save(any());
        assertThat(actual).isEqualTo(expected);

    }

    private WifiInfoDto createDto() {
        return new WifiInfoDto(
                "---GR00000",
                "구로구",
                "안양천공원",
                "구로동 621-8",
                "동양미래대학 앞 농구장",
                "",
                "3. 공원(하천)",
                "기타",
                "공공WiFi",
                "자가망_U무선망",
                "2016",
                "실외",
                "",
                "126.86959",
                "37.500145",
                "2023-04-15 10:58:19.0"
        );
    }

}