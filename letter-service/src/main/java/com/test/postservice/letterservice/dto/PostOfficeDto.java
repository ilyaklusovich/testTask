package com.test.postservice.letterservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class PostOfficeDto {
    Long id;

    @NotNull(message = "office name can not be null")
    private String name;

    @NotNull(message = "office address can not be null")
    private String address;

    @NotNull(message = "index can not be null")
    private int postCode;
}
