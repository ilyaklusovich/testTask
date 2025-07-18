package com.test.postservice.letterservice.controller;

import com.amazonaws.util.json.Jackson;
import com.test.postservice.letterservice.dto.PostOfficeDto;
import com.test.postservice.letterservice.entity.PostOffice;
import com.test.postservice.letterservice.kafka.KafkaSender;
import com.test.postservice.letterservice.mapper.PostOfficeMapper;
import com.test.postservice.letterservice.service.PostOfficeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PostOfficeController {
    private final PostOfficeService postOfficeService;
    private final KafkaSender kafkaSender;
    private final PostOfficeMapper postOfficeMapper;

    @GetMapping("/post-offices")
    public ResponseEntity<List<PostOffice>> getAll() {
        List<PostOffice> res = postOfficeService.findAll();
        System.out.println(res.toString());
        kafkaSender.sendMessage(Jackson.toJsonString(res.toString()), "posts");
        return ResponseEntity.ok()
                .body(res);
    }

    @RequestMapping(value = "/post-office", method = RequestMethod.POST)
    public ResponseEntity<PostOfficeDto> savePostItem(@Valid @RequestBody PostOfficeDto postOfficeDto) {
        PostOffice postOffice = postOfficeMapper.toEntity(postOfficeDto);

        if (!postOfficeService.create(postOffice)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostOfficeDto response = postOfficeMapper.toDto(postOffice);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("post-office/{id}")
    public ResponseEntity<PostOfficeDto> updateAtm(@Valid @RequestBody PostOfficeDto postOfficeDto,
                                                 @PathVariable Long id) {
        PostOffice postOffice = postOfficeMapper.toEntity(postOfficeDto);
        postOffice.setId(id);
        if (!postOfficeService.update(postOffice)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostOfficeDto response = postOfficeMapper.toDto(postOffice);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("post-office/{id}")
    public ResponseEntity<HttpStatus> deleteAtm(@PathVariable Long id) {
        if (!postOfficeService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
