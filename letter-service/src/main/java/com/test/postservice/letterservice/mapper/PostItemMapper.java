package com.test.postservice.letterservice.mapper;

import com.test.postservice.letterservice.dto.PostItemDto;
import com.test.postservice.letterservice.entity.PostItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface PostItemMapper {
    PostItem toEntity(PostItemDto dto);

    PostItemDto toDto(PostItem entity);



//    @Mapping(source = "postOffices.id", target = "post_item_offices_id")
//    void updateEntityFromDto(PostItemDto dto, @MappingTarget PostItem entity);
//
//    @Mapping(source = "postOffices[0].id", target = "post_item_offices_id")
//    void updateDTOFromEntity(PostItem atm, @MappingTarget PostItemDto atmDto);
}
