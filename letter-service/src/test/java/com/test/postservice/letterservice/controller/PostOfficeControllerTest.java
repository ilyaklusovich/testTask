package com.test.postservice.letterservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.postservice.letterservice.dto.PostOfficeDto;
import com.test.postservice.letterservice.entity.PostOffice;
import com.test.postservice.letterservice.mapper.PostOfficeMapper;
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
public class PostOfficeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    PostOffice postOffice;
    @MockitoBean
    PostOfficeService postOfficeService;
    @MockitoBean
    PostOfficeMapper postOfficeMapper;
    @Autowired
    ObjectMapper objectMapper;

    private final String ADDRESS = "ADDRESS";
    private final int POST_CODE = 222222;
    private final String POST_NAME = "test";
    private final long ID1 = 1L;
    private final long ID2 = 2L;


    @Test
    void getPostOffices() throws Exception {
        when(postOfficeService.findAll()).thenReturn(getPostOfficesList());
        mvc.perform(get("/api/post-offices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void addPostOffice() throws Exception {
        when(postOfficeMapper.toEntity(getPostOfficeDto())).thenReturn(getPostOfficesList().getFirst());
        PostOffice postOffice = getPostOfficesList().getFirst();
        when(postOfficeService.create(postOffice)).thenReturn(true);
        when(postOfficeMapper.toDto(postOffice)).thenReturn(getPostOfficeDto());
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/post-office", "{id}", 1)
                        .content(objectMapper.writeValueAsString(getPostOfficeDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(ADDRESS))
                .andExpect(jsonPath("$.name").value(POST_NAME));
    }

    private List<PostOffice> getPostOfficesList() {
        PostOffice one = PostOffice.builder()
                .address(ADDRESS)
                .postCode(POST_CODE)
                .name(POST_NAME)
                .build();
        PostOffice two = PostOffice.builder()
                .address(ADDRESS + ID2)
                .id(ID2)
                .build();
        return List.of(one, two);
    }

    private PostOfficeDto getPostOfficeDto() {
        return PostOfficeDto.builder()
                .id(ID1)
                .address(ADDRESS)
                .postCode(POST_CODE)
                .name(POST_NAME)
                .build();
    }
}