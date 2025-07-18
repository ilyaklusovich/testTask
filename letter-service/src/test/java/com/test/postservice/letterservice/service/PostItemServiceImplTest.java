package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.entity.PostItem;
import com.test.postservice.letterservice.entity.PostItemStatus;
import com.test.postservice.letterservice.entity.PostItemType;
import com.test.postservice.letterservice.exception.NotFoundException;
import com.test.postservice.letterservice.repository.PostItemRepository;
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
class PostItemServiceImplTest {

    @MockitoBean
    private PostItemRepository postItemRepository;
    @Autowired
    private PostItemService postItemService;

    private final String CHANGE_CITY = "MSC, Patriki 13";
    private final int POST_CODE = 222222;
    private final String NAME = "ru-pochta";
    long ID1 = 1L;
    long ID2 = 2L;
    @Test
    void createPostItem() {
        doReturn(getPostItem()).when(postItemRepository).saveAndFlush(getPostItem());
        boolean actualResult = postItemService.create(getPostItem());
        assertThat(actualResult).isEqualTo(true);
    }

    @Test
    void findAll() {
        List<PostItem> postItemList = new ArrayList<>();
        PostItem postItemMock = getPostItem();
        postItemMock.setAddress(CHANGE_CITY);
        postItemMock.setId(ID2);
        postItemList.add(getPostItem());
        postItemList.add(postItemMock);
        PostItem mockResult = PostItem.builder()
                .status(PostItemStatus.NONE)
                .address(CHANGE_CITY)
                .id(ID2)
                .name(NAME)
                .postCode(POST_CODE)
                .postItemType(PostItemType.POSTCARD)
                .status(PostItemStatus.SEND_TO_INTERMEDIATE)
                .build();
        when(postItemRepository.findAll()).thenReturn(postItemList);
        List<PostItem> actualResult = postItemService.findAll();
        assertEquals(getPostItem(), actualResult.get(0));
        assertEquals(mockResult, actualResult.get(1));
        assertEquals(postItemList.size(), actualResult.size());
    }

    @Test
    void findById() {
        when(postItemRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(getPostItem()));
        PostItem actualResult = postItemService.findById(ID1);
        assertEquals(getPostItem(), actualResult);
    }

    @Test
    void findByIdIsInvalid() {
        when(postItemRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException actualResult = assertThrowsExactly(NotFoundException.class, () -> postItemService.findById(ID1));
        assertEquals(NotFoundException.class, actualResult.getClass());
    }

    @Test
    void update() {
        when(postItemRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(getPostItem()));
        when(postItemRepository.save(any(PostItem.class))).then(returnsFirstArg());
        PostItem postItemToUpdate = PostItem
                .builder()
                .address(CHANGE_CITY)
                .status(PostItemStatus.ACCEPTED)
                .id(ID1)
                .name("Change City")
                .postCode(222222)
                .build();
        boolean actualResult = postItemService.update(postItemToUpdate);
        assertThat(actualResult).isEqualTo(true);
        assertThat(postItemToUpdate.getAddress()).isNotEqualTo(getPostItem().getAddress());
        assertThat(postItemToUpdate.getName()).isNotEqualTo(getPostItem().getName());
        assertThat(postItemToUpdate.getStatus()).isNotEqualTo(getPostItem().getStatus());
        assertThat(postItemToUpdate.getPostCode()).isEqualTo(getPostItem().getPostCode());
    }

    @Test
    void updateIsInvalid() {
        doReturn(Optional.empty()).when(postItemRepository).findById(ID1);
        boolean actual = postItemService.update(getPostItem());
        assertFalse(actual);
    }

    @Test
    void delete() {
        when(postItemRepository.findById(ID1)).thenReturn(Optional.ofNullable(getPostItem()));
        boolean actualResult = postItemService.delete(ID1);
        assertThat(actualResult).isEqualTo(true);
    }

    @Test
    void deleteIsInvalid() {
        when(postItemRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        boolean actualResult = postItemService.delete(ID1);
        assertThat(actualResult).isEqualTo(false);
    }

    private PostItem getPostItem() {
        String address = "SPB, rostovskaya 17/4";
        return PostItem.builder()
                .address(address)
                .status(PostItemStatus.SEND_TO_INTERMEDIATE)
                .postItemType(PostItemType.POSTCARD)
                .postCode(POST_CODE)
                .name(NAME)
                .build();
    }
}

