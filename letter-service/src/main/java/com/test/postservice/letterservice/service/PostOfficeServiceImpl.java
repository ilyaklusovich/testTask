package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.aop.Auditable;
import com.test.postservice.letterservice.aop.AuditingActionType;
import com.test.postservice.letterservice.aop.AuditingEntityType;
import com.test.postservice.letterservice.entity.PostOffice;
import com.test.postservice.letterservice.exception.BadRequestException;
import com.test.postservice.letterservice.exception.NotFoundException;
import com.test.postservice.letterservice.repository.PostOfficeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;

    @Override
    public List<PostOffice> findAll() {
        return postOfficeRepository.findAll();
    }

    @Override
    public PostOffice findById(long id) {
        log.info("office with ID{}", id);
        return postOfficeRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Office with id %d not found", id)));
    }

    @Auditable(actionType = AuditingActionType.CREATE, entityType = AuditingEntityType.POST_OFFICE)
    @Transactional
    @Override
    public boolean create(PostOffice postOffice) {
        Optional<PostOffice> postOfficeOptional = postOfficeRepository.findById(postOffice.getId());
        if (postOfficeOptional.isPresent()) {
            log.error("create post Office error {}", postOffice);
            throw new BadRequestException(String.format("post Office with id %d already exists",
                    postOffice.getId()));
        }
        postOffice.setId(null);
        postOfficeRepository.saveAndFlush(postOffice);
        log.info("create office {}", postOffice);
        return true;
    }
    @Auditable(actionType = AuditingActionType.UPDATE, entityType = AuditingEntityType.POST_OFFICE)
    @Transactional
    @Override
    public boolean update(PostOffice postOffice) {
        Optional<PostOffice> postOfficeOptional = postOfficeRepository.findById(postOffice.getId());
        if (postOfficeOptional.isPresent()) {
            postOfficeRepository.save(postOffice);
            log.info("update office with ID{}", postOffice.getId());
            return true;
        }
        log.error("update office error, ID{}", postOffice.getId());
        return false;
    }
    @Transactional
    @Override
    @Auditable(actionType = AuditingActionType.DELETE, entityType = AuditingEntityType.POST_OFFICE)

    public boolean delete(Long id) {
        if (postOfficeRepository.findById(id).isPresent()) {
            postOfficeRepository.deleteById(id);
            log.info("delete office with ID{}", id);
            return true;
        }
        log.error("delete office error, with id{}", id);
        return false;
    }
}
