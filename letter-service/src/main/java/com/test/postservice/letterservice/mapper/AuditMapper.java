package com.test.postservice.letterservice.mapper;

import com.test.postservice.letterservice.dto.AuditDto;
import com.test.postservice.letterservice.entity.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    AuditDto auditToAuditDto(Audit audit);

    Audit auditDtoToAudit(AuditDto auditDto);
}
