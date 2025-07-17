package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.dto.PostItemDto;
import com.test.postservice.letterservice.entity.PostItem;
import com.test.postservice.letterservice.mapper.PostItemMapper;
import com.test.postservice.letterservice.repository.PostItemRepository;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostItemServiceImpl implements PostItemService {

    private final PostItemRepository postItemRepository;

    public void update(PostItem postItem) {
        postItemRepository.save(postItem);
    }

    @Override
    public List<PostItemDto> findAll() {
        return List.of();
    }

    @Override
    public PostItemDto findById(long id) {
        return null;
    }

    @Override
    @Transactional
    public boolean create(PostItem postItem) {
        Optional<PostItem> postItemOptional = postItemRepository.findById(postItem.getId());
        if (postItemOptional.isPresent()) {
            log.error("create post item error {}", postItem);
            throw new BadRequestException(String.format("post item with id %d already exists",
                    postItem.getId()));
        }
        postItemRepository.saveAndFlush(postItem);
        log.info("create atm {}", postItem);
        return true;
    }

    @Override
    public PostItemDto update(Long id, PostItemDto accountTransferDto) {
        return null;
    }

    public void delete(Long id) {
        postItemRepository.deleteById(id);
    }
}
