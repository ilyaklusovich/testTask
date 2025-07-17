package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.aop.Auditable;
import com.test.postservice.letterservice.aop.AuditingActionType;
import com.test.postservice.letterservice.aop.AuditingEntityType;
import com.test.postservice.letterservice.entity.PostItem;
import com.test.postservice.letterservice.entity.PostItemStatus;
import com.test.postservice.letterservice.entity.PostOffice;
import com.test.postservice.letterservice.repository.PostItemRepository;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class PostItemServiceImpl implements PostItemService {

    private final PostItemRepository postItemRepository;

    public PostItemServiceImpl(PostItemRepository postItemRepository) {
        this.postItemRepository = postItemRepository;
    }

    @Override
    public List<PostItem> findAll() {
        return postItemRepository.findAll();
    }

    @Override
    public PostItem findById(long id) {
        log.info("item with ID{}", id);
        return postItemRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Item with id %d not found", id)));
    }

    @Transactional
    @Override
    @Auditable(actionType = AuditingActionType.CHANGE_STATUS, entityType = AuditingEntityType.POST_ITEM)
    public boolean create(PostItem postItem) {
        Optional<PostItem> postItemOptional = postItemRepository.findById(postItem.getId());
        if (postItemOptional.isPresent()) {
            log.error("create post item error {}", postItem);
            throw new BadRequestException(String.format("post item with id %d already exists",
                    postItem.getId()));
        }
        postItem.setId(null);
        postItem.setStatus(PostItemStatus.ACCEPTED);
        postItemRepository.saveAndFlush(postItem);
        log.info("create item {}", postItem);
        return true;
    }
    @Auditable(actionType = AuditingActionType.UPDATE, entityType = AuditingEntityType.POST_ITEM)
    @Transactional
    @Override
    public boolean update(PostItem postItem) {
        Optional<PostItem> postItemOptional = postItemRepository.findById(postItem.getId());
        if (postItemOptional.isPresent()) {
            postItemRepository.save(postItem);
            log.info("update item with ID{}", postItem.getId());
            return true;
        }
        log.error("update item error, ID{}", postItem.getId());
        return false;
    }
    @Auditable(actionType = AuditingActionType.DELETE, entityType = AuditingEntityType.POST_ITEM)
    @Transactional
    @Override
    public boolean delete(Long id) {
        if (postItemRepository.findById(id).isPresent()) {
            postItemRepository.deleteById(id);
            log.info("delete item with ID{}", id);
            return true;
        }
        log.error("delete item error, with id{}", id);
        return false;
    }
    @Auditable(actionType = AuditingActionType.CHANGE_STATUS, entityType = AuditingEntityType.POST_ITEM)
    @Override
    public void setPostOffices(PostOffice postOffice, Long id) {
        Optional<PostItem> postItem = postItemRepository.findById(id);
        if (postItem.isEmpty()) {
            return;
        }
        List<PostOffice> postOfficeList = postItem.get().getPostOffices();
        postOfficeList.add(postOffice);
        postItem.get().setPostOffices(postOfficeList);
        if (!Objects.equals(postItem.get().getPostCode(), postOffice.getPostCode())) {
            postItem.get().setStatus(PostItemStatus.SEND_TO_INTERMEDIATE);
        }
        postItemRepository.saveAndFlush(postItem.get());
    }
    @Auditable(actionType = AuditingActionType.CHANGE_STATUS, entityType = AuditingEntityType.POST_ITEM)
    public void finishedDelivery(long id) {
        Optional<PostItem> postItem = Optional.ofNullable(postItemRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Item with id %d not found", id))));
        if (postItem.isPresent()) {
            postItem.get().setStatus(PostItemStatus.FINISHED);
            postItemRepository.saveAndFlush(postItem.get());
        }
    }
}
