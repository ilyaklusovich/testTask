package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.entity.PostItem;
import com.test.postservice.letterservice.entity.PostOffice;

import java.util.List;

public interface PostItemService {
    List<PostItem> findAll();

    PostItem findById(long id);

    boolean create(PostItem postItem);

    boolean update(PostItem postItem); // Исправленная сигнатура

    boolean delete(Long id);

    void setPostOffices(PostOffice postOffice, Long id);

    void finishedDelivery(long id);
}
