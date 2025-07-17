package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.dto.PostOfficeDto;

import java.util.List;

public interface PostOfficeService {
    List<PostOfficeDto> findAll();
    PostOfficeDto findById(long id);
    PostOfficeDto create(PostOfficeDto postOfficeDto);
    PostOfficeDto update(Long id, PostOfficeDto postOfficeDto); // Исправленная сигнатура
    void delete(Long id);
}
