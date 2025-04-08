package com.example.userservice.kafka;

import com.example.userservice.event.FriendAddedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {
    private final KafkaTemplate<String, FriendAddedEvent> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, FriendAddedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFriendAddedEvent(FriendAddedEvent event) {
        kafkaTemplate.send("friend-added", event);
    }
}
