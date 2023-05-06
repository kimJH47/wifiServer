package com.core.wifiserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.core.wifiserver.dao.BookmarkListDao;
import com.core.wifiserver.dto.BookmarkListDto;
import com.core.wifiserver.dto.request.BookmarkSaveRequest;
import com.core.wifiserver.dto.request.Request;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookmarkListServiceTest {

    BookmarkListService bookmarkListService;
    BookmarkListDao bookmarkListDao;

    @BeforeEach
    void init() {
        bookmarkListDao = mock(BookmarkListDao.class);
        bookmarkListService = new BookmarkListService(bookmarkListDao);

    }

    @Test
    @DisplayName("bookmarkGroup id 와 wifiName 를 받아 저장되어야한다.")
    void save() throws Exception {
        //given
        int expected = 1;
        given(bookmarkListDao.save(anyString(), anyInt())).willReturn(expected);
        Request<BookmarkSaveRequest> request = new Request<>(new BookmarkSaveRequest(1, "wifiName"));
        //when
        Integer actual = bookmarkListService.save(request);
        //then
        then(bookmarkListDao).should(times(1)).save(anyString(), anyInt());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("북마크 정보를 담은 리스트 response 가 반환되어야한다.")
    void findAll() throws Exception {
        //given
        ArrayList<BookmarkListDto> bookmarkListDtos = new ArrayList<>();
        bookmarkListDtos.add(new BookmarkListDto(1, "groupName1", "wifi1", LocalDateTime.now()
                .toString()));
        bookmarkListDtos.add(new BookmarkListDto(2, "groupName2", "wifi2", LocalDateTime.now()
                .toString()));
        given(bookmarkListDao.findAll()).willReturn(bookmarkListDtos);
        //when
        List<BookmarkListDto> actual = bookmarkListService.findAll();
        //then
        then(bookmarkListDao).should(times(1)).findAll();
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getId()).isEqualTo(1);
        assertThat(actual.get(1).getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("id 를 받아 삭제된 데이터의 갯수를 response 를 반환해야한다.")
    void delete() throws Exception {
        //given
        int expected = 1;
        given(bookmarkListDao.delete(anyInt())).willReturn(expected);
        //when
        Integer acutal = bookmarkListService.delete(1);
        //then
        then(bookmarkListDao).should(times(1)).delete(anyInt());
        assertThat(acutal).isEqualTo(expected);
    }
}