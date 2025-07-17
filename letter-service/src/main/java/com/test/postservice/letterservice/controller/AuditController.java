package com.test.postservice.letterservice.controller;

import com.test.postservice.letterservice.dto.AuditDto;
import com.test.postservice.letterservice.entity.Audit;
import com.test.postservice.letterservice.mapper.AuditMapper;
import com.test.postservice.letterservice.service.AuditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "/audit")
@RestController
public class AuditController {
    private final AuditMapper auditMapper;
    private final AuditService auditService;

    public AuditController(AuditService auditService, AuditMapper auditMapper) {
        this.auditService = auditService;
        this.auditMapper = auditMapper;
    }

    @GetMapping()
    public ResponseEntity<List<AuditDto>> getAudit() {
        return new ResponseEntity<>(auditService.findAll().stream()
                .map(auditMapper::auditToAuditDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Audit> getAuditFromId(@PathVariable Long id) {
        return new ResponseEntity<>(auditService.findById(id), HttpStatus.OK);
    }

}
