package com.test.postservice.letterservice.dto;

import com.test.postservice.letterservice.entity.PostItemType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostItemDto {

    long id;

    @NotNull(message = "post item type can not be null")
    PostItemType postItemType;

    @NotNull(message = "post code can not be null")
    private String postCode;

    @NotNull(message = "address can not be null")
    private String address;

    @NotNull(message = "name can not be null")
    private String name;
}
