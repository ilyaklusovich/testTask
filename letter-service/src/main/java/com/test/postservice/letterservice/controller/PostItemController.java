package com.test.postservice.letterservice.controller;

import com.amazonaws.util.json.Jackson;
import com.test.postservice.letterservice.dto.PostItemDto;
import com.test.postservice.letterservice.entity.PostItem;
import com.test.postservice.letterservice.entity.PostItemStatus;
import com.test.postservice.letterservice.entity.PostOffice;
import com.test.postservice.letterservice.kafka.KafkaSender;
import com.test.postservice.letterservice.mapper.PostItemMapper;
import com.test.postservice.letterservice.service.PostItemService;
import com.test.postservice.letterservice.service.PostOfficeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PostItemController {
    private final PostItemService postItemService;
    private final KafkaSender kafkaSender;
    private final PostItemMapper postItemMapper;
    private final PostOfficeService postOfficeService;

    @GetMapping("/items")
    public ResponseEntity<List<PostItem>> getAll() {
        List<PostItem> res = postItemService.findAll();
        kafkaSender.sendMessage(Jackson.toJsonString(res.toString()), "posts");
        return ResponseEntity.ok()
                .body(res);
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public ResponseEntity<PostItemDto> savePostItem(@Valid @RequestBody PostItemDto postItemDto) {
        PostItem postItem = postItemMapper.toEntity(postItemDto);
        postItem.setStatus(PostItemStatus.NONE);
        if (!postItemService.create(postItem)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostItemDto response = postItemMapper.toDto(postItem);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("item/{id}")
    public ResponseEntity<PostItemDto> updateAtm(@Valid @RequestBody PostItemDto postItemDto,
                                              @PathVariable Long id) {
        PostItem postItem = postItemMapper.toEntity(postItemDto);
        postItem.setId(id);
        if (!postItemService.update(postItem)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostItemDto response = postItemMapper.toDto(postItem);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("item/{id}")
    public ResponseEntity<HttpStatus> deleteAtm(@PathVariable Long id) {
        if (!postItemService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("item/{itemId}/register/{officeId}")
    public ResponseEntity<HttpStatus> registerItem(@PathVariable Long itemId, @PathVariable Long officeId) {
        PostItem postItem = postItemService.findById(itemId);
        PostOffice postOffice = postOfficeService.findById(officeId);

        if (postItem == null || postOffice == null ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        postItemService.setPostOffices(postOffice, postItem.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/item/{id}/finished")
    public ResponseEntity<PostItemDto> finished(@PathVariable Long id) {
        PostItem postItem = postItemService.findById(id);
        postItem.setStatus(PostItemStatus.FINISHED);
        if (!postItemService.update(postItem)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostItemDto response = postItemMapper.toDto(postItem);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("item/{id}/history")
    public ResponseEntity<String> getHistory(@PathVariable Long id) {
        PostItem postItem = postItemService.findById(id);
        StringBuilder sb= new StringBuilder("History:");
        postItem.getPostOffices().stream().map(PostOffice::getName).forEach(item-> sb.append(item).append(" "));

        kafkaSender.sendMessage(Jackson.toJsonString(sb.append(postItem.getStatus()).toString()), "posts");
        return ResponseEntity.ok()
                .body(sb.toString());
    }
}
