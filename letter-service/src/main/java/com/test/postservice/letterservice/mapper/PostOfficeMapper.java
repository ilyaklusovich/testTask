package com.test.postservice.letterservice.mapper;

import com.test.postservice.letterservice.dto.PostOfficeDto;
import com.test.postservice.letterservice.entity.PostOffice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostOfficeMapper {
    @Mapping(target = "id", ignore = true)
    PostOffice toEntity(PostOfficeDto dto);

    PostOfficeDto toDto(PostOffice entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(PostOfficeDto dto, @MappingTarget PostOffice entity);
}
