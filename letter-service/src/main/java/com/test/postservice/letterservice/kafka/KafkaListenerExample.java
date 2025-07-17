package com.test.postservice.letterservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListenerExample {

    @KafkaListener(topics = "posts", groupId = "myGroup")
    void listener(String data) {
        log.info("Received message [{}] in group1", data);
    }
}
