package com.test.postservice.letterservice.repository;

import com.test.postservice.letterservice.entity.PostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostItemRepository extends JpaRepository<PostItem, Long> {
}
