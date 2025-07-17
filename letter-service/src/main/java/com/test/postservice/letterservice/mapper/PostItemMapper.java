package com.test.postservice.letterservice.mapper;

import com.test.postservice.letterservice.dto.PostItemDto;
import com.test.postservice.letterservice.entity.PostItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostItemMapper {
    PostItem toEntity(PostItemDto dto);

    PostItemDto toDto(PostItem entity);
}