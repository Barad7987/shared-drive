package com.example.shared_drive.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    UUID id;
    String name;
    String email;
    List<UserFileAccess> files;
}
