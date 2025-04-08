package com.example.userservice.event;

import lombok.Data;

import java.util.UUID;

@Data
public class FriendAddedEvent {
    private UUID userId;
    private UUID friendId;
}
