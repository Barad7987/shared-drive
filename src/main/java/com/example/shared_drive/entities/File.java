package com.example.shared_drive.entities;

import com.example.shared_drive.enums.FileStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class File {
    private UUID id;
    private String name;
    // fileSize in bytes
    private long fileSize;
    private String mimeType;
    private Instant uploadedAt;
    private Instant modifiedAt;
    private boolean isDirectory;
    private FileStatus status;

    private String url;

    // file's parent directory null if it is root directory
    private File parent;

    private List<File> files;

    private List<UserFileAccess> sharedWith;
}
