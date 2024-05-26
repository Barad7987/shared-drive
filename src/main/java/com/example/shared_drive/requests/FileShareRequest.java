package com.example.shared_drive.requests;

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
public class FileShareRequest {
    UUID fileId;
    UUID userId;
    FileAccess access;
}
