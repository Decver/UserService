package com.example.userservice.service;

import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.dto.request.RegisterRequest;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.model.User;

import java.util.Set;
import java.util.UUID;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
    String loginUser(LoginRequest request);
    UserResponse getUserById(UUID id);
    UserResponse updateUser(UUID id, RegisterRequest request);
    void deleteUser(UUID id);
    UserResponse mapToUserResponse(User user);

    void addFriend(UUID userId, UUID friendId);
    void removeFriend(UUID userId, UUID friendId);
    boolean isFriend(UUID userId, UUID friendId);
    Set<UUID> getIdFriends(UUID userId, UUID friendId);
}
