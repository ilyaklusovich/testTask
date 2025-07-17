package com.test.postservice.letterservice.repository;

import com.test.postservice.letterservice.entity.PostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostItemRepository extends JpaRepository<PostItem, Long> {
}
