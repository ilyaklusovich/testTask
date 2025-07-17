package com.test.postservice.letterservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "posts", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}