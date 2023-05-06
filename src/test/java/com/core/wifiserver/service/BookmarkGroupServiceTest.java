package com.core.wifiserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.core.wifiserver.dao.BookmarkGroupDao;
import com.core.wifiserver.dto.BookmarkGroupDto;
import com.core.wifiserver.dto.request.Request;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookmarkGroupServiceTest {
    BookmarkGroupService bookmarkGroupService;
    BookmarkGroupDao bookmarkGroupDao;

    @BeforeEach
    void init() {
        bookmarkGroupDao = mock(BookmarkGroupDao.class);
        bookmarkGroupService = new BookmarkGroupService(bookmarkGroupDao);
    }

    @Test
    @DisplayName("BookmarkGroup save request 를 받으면 저장 후 1이 반환 되어야한다.")
    void save() throws Exception {
        //given
        Request<BookmarkGroupDto> any = new Request<>(
                new BookmarkGroupDto(-1, "book", 1, LocalDateTime.now().toString(), null));
        int expected = 1;
        given(bookmarkGroupDao.save(anyString(), anyInt())).willReturn(expected);
        //when
        Integer actual = bookmarkGroupService.save(any);
        //then
        then(bookmarkGroupDao).should(times(1)).save(anyString(), anyInt());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("북마크 그룹 전체가 반환해야한다.")
    void findAll() throws Exception {
        //given
        ArrayList<BookmarkGroupDto> expected = new ArrayList<>();
        expected.add(new BookmarkGroupDto(1, "list1", 1, LocalDateTime.now().toString(), null));
        expected.add(new BookmarkGroupDto(2, "list2", 2, LocalDateTime.now().toString(), null));
        expected.add(new BookmarkGroupDto(3, "list3", 3, LocalDateTime.now().toString(), null));
        given(bookmarkGroupDao.findAll()).willReturn(expected);

        //when
        List<BookmarkGroupDto> actual = bookmarkGroupService.findAll();
        //then
        then(bookmarkGroupDao).should(times(1)).findAll();
        assertThat(actual).hasSize(expected.size())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("북마크 그룹 id 를 받으면 해당 id 에 해당하는 그룹을 삭제하고 1을 반환해야한다.")
    void delete() throws Exception {
        //given
        int expected = 1;
        given(bookmarkGroupDao.delete(anyInt())).willReturn(expected);
        //when
        Integer actual = bookmarkGroupService.delete(1);
        //actual
        then(bookmarkGroupDao).should(times(1)).delete(anyInt());
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("북마크 그룹 수정시 order 가 재정렬된 후 저장되어야 한다.")
    void update() throws Exception {
        //given
        int expected = 1;
        given(bookmarkGroupDao.update(anyInt(), anyInt(), anyString())).willReturn(expected);
        Request<BookmarkGroupDto> req = new Request<>(
                new BookmarkGroupDto(1, "kim", 3, LocalDateTime.now().toString(), null));
        //when
        Integer actual = bookmarkGroupService.update(req);
        //then
        then(bookmarkGroupDao).should(times(1)).update(anyInt(), anyInt(), anyString());
        assertThat(actual).isEqualTo(expected);

    }
}