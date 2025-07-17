package com.test.postservice.letterservice.repository;

import com.test.postservice.letterservice.entity.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {
}
