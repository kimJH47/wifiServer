package com.core.wifiserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.core.wifiserver.dao.HistoryDao;
import com.core.wifiserver.dto.HistoryDto;
import com.core.wifiserver.dto.request.HistorySaveRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HistoryServiceTest {

    HistoryService historyService;
    HistoryDao historyDao;

    @BeforeEach
    void init() {
        historyDao = mock(HistoryDao.class);
        historyService = new HistoryService(historyDao);
    }

    @Test
    @DisplayName("HistoryRequest 를 받아 저장된 컬럼의 갯수를 담은 response 를 반환해야한다.")
    void save() throws Exception {
        //given
        int expected = 1;
        given(historyDao.save(anyDouble(), anyDouble())).willReturn(expected);
        HistorySaveRequest request = new HistorySaveRequest(40.3, 30.134);
        //when
        Integer actual = historyService.save(request);
        //then
        then(historyDao).should(times(1)).save(anyDouble(), anyDouble());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("삭제랑 데이터의 id 를 받아 삭제된 컬럼의 갯수를 담은 response 를 반환해야한다.")
    void delete() throws Exception {
        //given
        int expected = 1;
        given(historyDao.delete(anyInt())).willReturn(expected);
        //when
        Integer actual = historyService.delete(expected);
        //then
        then(historyDao).should(times(1)).delete(anyInt());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("히스토리 전체데이터을 담은 response 가 반환되어야한다.")
    void findAll() throws Exception {
        //given
        ArrayList<HistoryDto> expected = new ArrayList<>();
        expected.add(new HistoryDto(1, 30.20, 120.123, LocalDateTime.now().toString()));
        expected.add(new HistoryDto(2, 31.20, 120.123, LocalDateTime.now().toString()));
        expected.add(new HistoryDto(3, 32.20, 120.123, LocalDateTime.now().toString()));
        given(historyDao.findAll()).willReturn(expected);
        //when
        List<HistoryDto> actual = historyService.findAll();

        //then
        then(historyDao).should(times(1)).findAll();
        assertThat(actual).hasSize(expected.size());
    }

}