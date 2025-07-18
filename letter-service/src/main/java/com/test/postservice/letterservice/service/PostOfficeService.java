package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.entity.PostOffice;

import java.util.List;

public interface PostOfficeService {
    List<PostOffice> findAll();

    PostOffice findById(long id);

    boolean create(PostOffice postOffice);

    boolean update(PostOffice postOffice); // Исправленная сигнатура

    boolean delete(Long id);
}
