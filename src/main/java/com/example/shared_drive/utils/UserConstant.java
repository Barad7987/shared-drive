package com.example.shared_drive.utils;

import com.example.shared_drive.entities.User;

import java.util.UUID;

public class UserConstant {

    public static final User JOHN = User.builder()
            .id(UUID.fromString("a872de43-bf5b-4b49-bfd7-a8f98d1da97a"))
            .name("John Doe")
            .email("john@gmail.com")
            .build();

    public static final User JANE = User.builder()
            .id(UUID.fromString("a872de43-bf5b-4b49-bfd7-a8f98d1da97b"))
            .name("Jane Doe")
            .email("jane@gmail.com")
            .build();
}
