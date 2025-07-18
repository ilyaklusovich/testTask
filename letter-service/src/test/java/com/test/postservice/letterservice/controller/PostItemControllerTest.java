package com.test.postservice.letterservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.postservice.letterservice.dto.PostItemDto;
import com.test.postservice.letterservice.entity.PostItem;
import com.test.postservice.letterservice.entity.PostItemStatus;
import com.test.postservice.letterservice.entity.PostItemType;
import com.test.postservice.letterservice.mapper.PostItemMapper;
import com.test.postservice.letterservice.service.PostItemService;
import com.test.postservice.letterservice.service.PostOfficeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostItemControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    PostItemMapper postItemMapper;
    @MockitoBean
    PostItemService postItemService;
    @MockitoBean
    PostOfficeService postOfficeService;
    @Autowired
    ObjectMapper objectMapper;

    private final String ADDRESS = "ADDRESS";
    private final int POST_CODE = 222222;
    private final String POST_NAME = "test";
    private final long ID1 = 1L;
    private final long ID2 = 2L;


    @Test
    void getPostItems() throws Exception {
        when(postItemService.findAll()).thenReturn(getPostItemLIst());
        mvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void addPostItem() throws Exception {
        when(postItemMapper.toEntity(getPostItemsDto())).thenReturn(getPostItemLIst().getFirst());
        PostItem atm = getPostItemLIst().getFirst();
        when(postItemService.create(atm)).thenReturn(true);
        when(postItemMapper.toDto(atm)).thenReturn(getPostItemsDto());
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/item", "{id}", 1)
                        .content(objectMapper.writeValueAsString(getPostItemsDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(ADDRESS))
                .andExpect(jsonPath("$.name").value(POST_NAME));
    }

    private List<PostItem> getPostItemLIst() {
        PostItem one = PostItem.builder()
                .status(PostItemStatus.NONE)
                .address(ADDRESS)
                .postCode(POST_CODE)
                .name(POST_NAME)
                .build();
        PostItem two = PostItem.builder()
                .status(PostItemStatus.ACCEPTED)
                .address(ADDRESS + ID2)
                .id(ID2)
                .build();
        return List.of(one, two);
    }

    private PostItemDto getPostItemsDto() {
        return PostItemDto.builder()
                .status(PostItemStatus.NONE)
                .id(ID1)
                .address(ADDRESS)
                .postCode(POST_CODE)
                .name(POST_NAME)
                .status(PostItemStatus.ACCEPTED)
                .postItemType(PostItemType.POSTCARD)
                .build();
    }
}