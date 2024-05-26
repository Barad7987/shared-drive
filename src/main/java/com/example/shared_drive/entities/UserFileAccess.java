package com.example.shared_drive.entities;

import com.example.shared_drive.enums.FileAccess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserFileAccess {
    private UUID id;
    private File file;
    private User user;
    private FileAccess access;
}
