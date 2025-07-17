package com.test.postservice.letterservice.controller;

import com.test.postservice.letterservice.dto.PostItemDto;
import com.test.postservice.letterservice.entity.PostItem;
import com.test.postservice.letterservice.kafka.KafkaSender;
import com.test.postservice.letterservice.mapper.PostItemMapper;
import com.test.postservice.letterservice.service.PostItemService;
import com.test.postservice.letterservice.service.PostOfficeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PostItemController {
    private final PostItemService postItemService;
    private final KafkaSender kafkaSender;
    private final PostItemMapper postItemMapper;
    private final PostOfficeService postOfficeService;

    @GetMapping("/items")
    public ResponseEntity<List<PostItemDto>> getAll() {
        List<PostItemDto> res = postItemService.findAll();
        kafkaSender.sendMessage("posts", "posts");
        return ResponseEntity.ok()
                .body(res);
    }

    @RequestMapping(value = "/item",method = RequestMethod.POST)
    public ResponseEntity<PostItemDto> savePostItem(@Valid @RequestBody PostItemDto postItemDto) {
        PostItem postItem = postItemMapper.toEntity(postItemDto);
        System.out.println(postItemDto.toString());
        System.out.println(postItem.toString());

        //postItemMapper.updateEntityFromDto(postItemDto, postItem);
        //postItem.setPostOffices(postOfficeService.findById(postItemDto.get()));

        if (!postItemService.create(postItem)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostItemDto response = postItemMapper.toDto(postItem);
       // postItemMapper.updateDTOFromEntity(postItem, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/item")
    public ResponseEntity<String> updatePostItem(@RequestBody PostItemDto dto) {
//        postItemService.update(dto.getId(), dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deletePostItem(@RequestBody Long id) {
        postItemService.delete(id);
        return ResponseEntity.ok().build();
    }
}
