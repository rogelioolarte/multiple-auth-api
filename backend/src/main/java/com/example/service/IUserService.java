package com.example.service;

import com.example.entity.User;

import java.util.UUID;

public interface IUserService extends IGenericService<User, UUID> {
    User findByUsername(String username);
    User register(User user);
    boolean existsByUsername(String username);
}
