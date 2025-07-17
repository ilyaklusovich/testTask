package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.entity.Audit;
import com.test.postservice.letterservice.repository.AuditRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImp implements AuditService {

   private final AuditRepository auditRepository;

    @Override
    public List<Audit> findAll() {
        log.info("get all audits");
        return auditRepository.findAll();
    }

    @Override
    public Audit findById(Long id) {
        log.info("get audit with ID{}", id);
        return auditRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Audit with id %d not found", id)));
    }

    @Transactional
    @Override
    public boolean save(Audit audit) {
        Optional<Audit> auditOptional = auditRepository.findById(audit.getId());
        if (auditOptional.isPresent()) {
            log.error("create audit error {}", audit);
            throw new BadRequestException(String.format("Audit with id %d already exists",
                    audit.getId()));
        }
        auditRepository.saveAndFlush(audit);
        log.info("create audit {}", audit);
        return true;
    }

    @Transactional
    @Override
    public boolean update(Audit audit) {
        Optional<Audit> auditOptional = auditRepository.findById(audit.getId());
        if (auditOptional.isPresent()) {
            auditRepository.save(audit);
            log.info("update audit with ID{}", audit.getId());
            return true;
        }
        log.error("update audit error, ID{}", audit.getId());
        return false;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        if (auditRepository.findById(id).isPresent()) {
            auditRepository.deleteById(id);
            log.info("delete audit with ID{}", id);
            return true;
        }
        log.error("delete audit error, with id{}", id);
        return false;
    }
}
