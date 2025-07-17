package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.dto.PostItemDto;
import com.test.postservice.letterservice.entity.PostItem;

import java.util.List;

public interface PostItemService {
    List<PostItemDto> findAll();

    PostItemDto findById(long id);

    boolean create(PostItem postItem);

    PostItemDto update(Long id, PostItemDto postItemDto); // Исправленная сигнатура

    void delete(Long id);
}
