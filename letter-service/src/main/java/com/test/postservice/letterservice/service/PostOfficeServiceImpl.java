package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.dto.PostOfficeDto;
import com.test.postservice.letterservice.entity.PostOffice;
import com.test.postservice.letterservice.mapper.PostOfficeMapper;
import com.test.postservice.letterservice.repository.PostOfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;
    private  PostOfficeMapper postOfficeMapper;

    @Override
    public List<PostOfficeDto> findAll() {
        return List.of();
    }

    @Override
    public PostOfficeDto findById(long id) {
        return null;
    }

    public PostOfficeDto create(PostOfficeDto postOfficeDto) {
        log.info("Creating new post item");
        PostOffice entity = postOfficeMapper.toEntity(postOfficeDto);
        PostOffice savedEntity = postOfficeRepository.save(entity);
        return postOfficeMapper.toDto(savedEntity);
    }

    @Override
    public PostOfficeDto update(Long id, PostOfficeDto postOfficeDto) {
        return null;
    }

    public void update(PostOffice postOffice) {
        postOfficeRepository.save(postOffice);
    }

    public void delete(Long id) {
        postOfficeRepository.deleteById(id);
    }
}
