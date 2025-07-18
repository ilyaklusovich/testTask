package com.test.postservice.letterservice.service;

import com.test.postservice.letterservice.entity.PostItem;
import com.test.postservice.letterservice.entity.PostOffice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostItemService {
    List<PostItem> findAll();

    PostItem findById(long id);

    boolean create(PostItem postItem);

    boolean update(PostItem postItem); // Исправленная сигнатура

    boolean delete(Long id);

    void setPostOffices(PostOffice postOffice, Long id);

    void finishedDelivery(long id);
}
