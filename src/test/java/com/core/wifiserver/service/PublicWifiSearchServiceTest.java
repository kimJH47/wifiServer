package com.core.wifiserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.core.wifiserver.client.PublicApiResponseClient;
import com.core.wifiserver.client.dto.ResponseEntity;
import com.core.wifiserver.dao.Page;
import com.core.wifiserver.dao.WifiInfoDao;
import com.core.wifiserver.dto.WifiDto;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.request.WifiSearchRequest;
import com.core.wifiserver.dto.response.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PublicWifiSearchServiceTest {

    PublicWifiSearchService publicWifiSearchService;
    PublicApiResponseClient publicApiResponseClient;
    WifiInfoDao wifiInfoDao;

    @BeforeEach
    void init() {
        publicApiResponseClient = mock(PublicApiResponseClient.class);
        wifiInfoDao = mock(WifiInfoDao.class);
        publicWifiSearchService = new PublicWifiSearchService(publicApiResponseClient, wifiInfoDao);
    }

    @Test
    @DisplayName("addPublicWifi 메서드 호출 시 서울시 공공API 데이터를 응답받고 DB에 저장되어야 한다.")
    void load() throws Exception {
        //given
        long firstCall = 999L;
        long secondCall = 1L;
        int expected = (int) (firstCall + secondCall);
        given(publicApiResponseClient.getApiTotalCount()).willReturn(expected);
        given(publicApiResponseClient.getPublicWifiList(anyInt(), anyInt()))
                .willReturn(
                        new ResponseEntity(expected, "101", new ArrayList<>(Collections.nCopies(999, null)))
                        , new ResponseEntity(expected, "101", new ArrayList<>(Collections.nCopies(1, null))));
        given(wifiInfoDao.save(any())).willReturn(firstCall, secondCall);
        //when
        Response<Integer> response = publicWifiSearchService.addPublicWifi();
        Integer actual = response.getEntity();
        //then
        then(publicApiResponseClient).should(times(1)).getApiTotalCount();
        then(publicApiResponseClient).should(times(2)).getPublicWifiList(anyInt(), anyInt());
        then(wifiInfoDao).should(times(2)).save(any());
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("페이징 정보가 포함된 request 를 받으면 사이즈에 맞게 와이파이정보를 반환해야한다.")
    public void findOrderByCoordinateWithPagination() throws Exception {
        //given
        Page page = new Page(1, 20);
        WifiSearchRequest wifiSearchRequest = new WifiSearchRequest(page, 35.123, 120.123);
        List<WifiDto> wifiInfoDto = new ArrayList<>();
        wifiInfoDto.add(createDto(30.10, "---GR00000"));
        wifiInfoDto.add(createDto(30.11, "---GR00001"));
        wifiInfoDto.add(createDto(30.12, "---GR00002"));

        given(wifiInfoDao.findOrderByCoordinateWithPagination(anyDouble(), anyDouble(), any(Page.class)))
                .willReturn(wifiInfoDto);
        //when
        Response<List<WifiDto>> response = publicWifiSearchService.findOrderByCoordinateWithPagination(
                new Request<>(wifiSearchRequest));
        //then
        then(wifiInfoDao).should(times(1)).findOrderByCoordinateWithPagination
                (anyDouble(), anyDouble(), any(Page.class));
        List<WifiDto> actual = response.getEntity();
        assertThat(actual).hasSize(3);
        assertThat(actual.get(0).getMgrNo()).isEqualTo("---GR00000");
    }

    @Test
    @DisplayName("mgrNo 를 받아 해당하는 공공와이파이데이터 정보 하나를 반환해야한다.")
    public void findOne() throws Exception{
        //given
        String expected = "--GR000005";
        given(wifiInfoDao.findOne(anyString(), anyDouble(), anyDouble()))
                .willReturn(createDto(50.10, expected));
        //when
        WifiDto actual = publicWifiSearchService.findOne("--GR000005", 30.13, 120.213)
                .getEntity();
        //then
        then(wifiInfoDao).should(times(1))
                .findOne(anyString(), anyDouble(), anyDouble());
        assertThat(actual.getMgrNo()).isEqualTo(expected);

    }

    private WifiDto createDto(double distance, String mgrNo) {
        return new WifiDto(
                distance,
                mgrNo,
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

