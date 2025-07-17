package com.test.postservice.letterservice.mapper;

import com.test.postservice.letterservice.dto.PostOfficeDto;
import com.test.postservice.letterservice.entity.PostOffice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostOfficeMapper {
    PostOffice toEntity(PostOfficeDto dto);

    PostOfficeDto toDto(PostOffice entity);
}
