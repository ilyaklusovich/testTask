package com.test.postservice.letterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AuditDto {
    private String entityType;
    private String operationType;
    private String  entityJson;
    private Date createdAt;
}
