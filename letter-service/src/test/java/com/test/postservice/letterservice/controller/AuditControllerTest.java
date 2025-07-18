package com.test.postservice.letterservice.controller;

import com.test.postservice.letterservice.dto.AuditDto;
import com.test.postservice.letterservice.entity.Audit;
import com.test.postservice.letterservice.mapper.AuditMapper;
import com.test.postservice.letterservice.service.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuditControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    AuditMapper auditMapper;
    @MockitoBean
    AuditService auditService;

    String URL = "/audit";
    long ID1 = 1L;
    long ID2 = 2L;
    String EXPECTED_DATE = "2005-06-04T09:15:00.000+00:00";
    Date DATE = new Date(1117876500000L);
    String OPERATION_TYPE = "UPDATE";
    String ENTITY_TYPE = "POST_OFFICE";
    String JSON = "PostItem(id=1, address=SPB)";

    @Test
    void getAudit() throws Exception {
        when(auditService.findAll()).thenReturn(getAuditsList());
        mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getAuditFromId() throws Exception {
        when(auditMapper.auditDtoToAudit(getAuditDto())).thenReturn(getAuditsList().get(0));
        when(auditService.findById(1L)).thenReturn(getAuditsList().get(0));
        when(auditMapper.auditToAuditDto(getAuditsList().get(0))).thenReturn(getAuditDto());
        mvc.perform(get(URL + "/{id}", ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationType").value(OPERATION_TYPE))
                .andExpect(jsonPath("$.entityJson").value(JSON))
                .andExpect(jsonPath("$.createdAt").value(EXPECTED_DATE));
    }

    private List<Audit> getAuditsList() {
        Audit one = Audit.builder()
                .id(ID1)
                .operationType(OPERATION_TYPE)
                .entityJson(JSON)
                .createdAt(DATE)
                .build();
        Audit two = Audit.builder()
                .id(ID2)
                .operationType(OPERATION_TYPE)
                .entityJson(JSON)
                .createdAt(DATE)
                .build();
        return List.of(one, two);
    }

    private AuditDto getAuditDto() {
        return AuditDto.builder()
                .operationType(OPERATION_TYPE)
                .entityJson(JSON)
                .createdAt(DATE)
                .entityType(ENTITY_TYPE)
                .build();
    }
}