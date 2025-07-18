package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.entity.PostOffice;
import com.test.postservice.letterservice.exception.NotFoundException;
import com.test.postservice.letterservice.repository.PostOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
@SpringBootTest(classes = {com.test.postservice.letterservice.LetterServiceApplication.class})
@ExtendWith(SpringExtension.class)
class PostOfficeServiceImplTest {

    @MockitoBean
    private PostOfficeRepository postOfficeRepository;
    @Autowired
    private PostOfficeService postOfficeService;

    private final String CHANGE_CITY = "MSC, Patriki 13";
    private final int POST_CODE = 222222;
    private final String NAME = "ru-pochta";
    long ID1 = 1L;
    long ID2 = 2L;
    @Test
    void createPostItem() {
        doReturn(getPostOffice()).when(postOfficeRepository).saveAndFlush(getPostOffice());
        boolean actualResult = postOfficeService.create(getPostOffice());
        assertThat(actualResult).isEqualTo(true);
    }

    @Test
    void findAll() {
        List<PostOffice> postOfficesList = new ArrayList<>();
        PostOffice postOfficeMock = getPostOffice();
        postOfficeMock.setAddress(CHANGE_CITY);
        postOfficeMock.setId(ID2);
        postOfficesList.add(getPostOffice());
        postOfficesList.add(postOfficeMock);
        PostOffice mockResult = PostOffice.builder()
                .address(CHANGE_CITY)
                .id(ID2)
                .name(NAME)
                .postCode(POST_CODE)
                .build();
        when(postOfficeRepository.findAll()).thenReturn(postOfficesList);
        List<PostOffice> actualResult = postOfficeService.findAll();
        assertEquals(getPostOffice(), actualResult.get(0));
        assertEquals(mockResult, actualResult.get(1));
        assertEquals(postOfficesList.size(), actualResult.size());
    }

    @Test
    void findById() {
        when(postOfficeRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(getPostOffice()));
        PostOffice actualResult = postOfficeService.findById(ID1);
        assertEquals(getPostOffice(), actualResult);
    }

    @Test
    void findByIdIsInvalid() {
        when(postOfficeRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException actualResult = assertThrowsExactly(NotFoundException.class, () -> postOfficeService.findById(ID1));
        assertEquals(NotFoundException.class, actualResult.getClass());
    }

    @Test
    void update() {
        when(postOfficeRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(getPostOffice()));
        when(postOfficeRepository.save(any(PostOffice.class))).then(returnsFirstArg());
        PostOffice postOfficeToUpdate = PostOffice
                .builder()
                .address(CHANGE_CITY)
                .id(ID1)
                .name("Change City")
                .postCode(222222)
                .build();
        boolean actualResult = postOfficeService.update(postOfficeToUpdate);
        assertThat(actualResult).isEqualTo(true);
        assertThat(postOfficeToUpdate.getAddress()).isNotEqualTo(getPostOffice().getAddress());
        assertThat(postOfficeToUpdate.getName()).isNotEqualTo(getPostOffice().getName());
        assertThat(postOfficeToUpdate.getPostCode()).isEqualTo(getPostOffice().getPostCode());
    }

    @Test
    void updateIsInvalid() {
        doReturn(Optional.empty()).when(postOfficeRepository).findById(ID1);
        boolean actual = postOfficeService.update(getPostOffice());
        assertFalse(actual);
    }

    @Test
    void delete() {
        when(postOfficeRepository.findById(ID1)).thenReturn(Optional.ofNullable(getPostOffice()));
        boolean actualResult = postOfficeService.delete(ID1);
        assertThat(actualResult).isEqualTo(true);
    }

    @Test
    void deleteIsInvalid() {
        when(postOfficeRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        boolean actualResult = postOfficeService.delete(ID1);
        assertThat(actualResult).isEqualTo(false);
    }

    private PostOffice getPostOffice() {
        String address = "SPB, rostovskaya 17/4";
        return PostOffice.builder()
                .address(address)
                .postCode(POST_CODE)
                .name(NAME)
                .build();
    }
}

