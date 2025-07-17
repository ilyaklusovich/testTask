package com.test.postservice.letterservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PostOfficeDto {
    long id;

    @NotNull(message = "office name can not be null")
    private String name;

    @NotNull(message = "office address can not be null")
    private String address;
}
