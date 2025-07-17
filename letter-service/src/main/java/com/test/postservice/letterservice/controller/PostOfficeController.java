package com.test.postservice.letterservice.controller;

import com.test.postservice.letterservice.dto.PostOfficeDto;
import com.test.postservice.letterservice.service.PostOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostOfficeController {
    private final PostOfficeService postOfficeService;

    @GetMapping("/offices/all")
    public ResponseEntity<List<PostOfficeDto>> getAll() {

        List<PostOfficeDto> res = postOfficeService.findAll();

        return ResponseEntity.ok()
                .body(res);
    }

    @PostMapping
    public ResponseEntity<String> savePostOffice(@RequestBody PostOfficeDto dto) {
        postOfficeService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<String> updatePostOffice(@RequestBody PostOfficeDto dto) {
        postOfficeService.update(dto.getId(), dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<String> deletePostOffice(@RequestBody PostOfficeDto dto) {
        postOfficeService.delete(dto.getId());
        return ResponseEntity.ok().build();
    }
}
