package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.entity.Audit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuditService {
    List<Audit> findAll();

    Audit findById(Long id);

    boolean save(Audit audit);

    boolean update(Audit audit);

    boolean delete(Long id);
}
