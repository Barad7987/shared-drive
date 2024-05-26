package com.example.shared_drive.services;

import com.example.shared_drive.entities.File;
import com.example.shared_drive.entities.User;
import com.example.shared_drive.entities.UserFileAccess;
import com.example.shared_drive.enums.FileAccess;
import com.example.shared_drive.enums.FileStatus;
import com.example.shared_drive.requests.FileCreateRequest;
import com.example.shared_drive.requests.FileShareRequest;
import com.example.shared_drive.requests.FileUploadRequest;
import com.example.shared_drive.utils.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class FileService {


    private static final List<UserFileAccess> OWNER = List.of(UserFileAccess.builder()
            .access(FileAccess.OWNER)
            .user(UserConstant.JOHN)
            .build());

    private static final String FOLDER_MIME_TYPE = "application/folder";


    @Autowired
    FileDbPersistenceService fileDbPersistenceService;

    @Autowired
    FileBucketPersistenceService fileBucketPersistenceService;

    public File uploadFile(FileUploadRequest request) {
        var metadata = File.builder()
                .fileSize(request.getFile().getSize())
                .mimeType(request.getFile().getContentType())
                .name(request.getFile().getOriginalFilename())
                .uploadedAt(Instant.now())
                .modifiedAt(Instant.now())
                .isDirectory(false)
                .status(FileStatus.UPLOADING)
                .sharedWith(new ArrayList<>(OWNER))
                .parent(request.getParentId() != null ? fileDbPersistenceService.getFileDetails(request.getParentId()) : null)
                .build();

        var file = fileDbPersistenceService.addFile(metadata);

        // upload file to S3 bucket
        fileBucketPersistenceService.uploadAsync(request.getFile())
                .thenAccept(fileKey -> {
                    file.setStatus(FileStatus.UPLOADED);
                    file.setUrl(fileKey);
                    fileDbPersistenceService.updateFile(file.getId(), file);
                }).exceptionally(throwable -> {
                    fileDbPersistenceService.updateFileStatus(file.getId(), FileStatus.ERROR);
                    return null;
                });

        return parseFile(file);

    }

    private File parseFile(File file) {
        return File.builder()
                .id(file.getId())
                .name(file.getName())
                .fileSize(file.getFileSize())
                .mimeType(file.getMimeType())
                .uploadedAt(file.getUploadedAt())
                .modifiedAt(file.getModifiedAt())
                .status(file.getStatus())
                .url(file.getUrl())
                .isDirectory(file.isDirectory())
                .build();
    }


    public List<File> getFiles(UUID parentId) {
        return fileDbPersistenceService.getFiles(parentId)
                .stream()
                .map(this::parseFile)
                .toList();
    }


    public File createFile(FileCreateRequest request) {
        var parentDirectory = request.getParentId() == null ? null : fileDbPersistenceService.getFileDetails(request.getParentId());
        var fileMetadata = File.builder()
                .parent(parentDirectory)
                .name(request.getName())
                .isDirectory(true)
                .uploadedAt(Instant.now())
                .modifiedAt(Instant.now())
                .mimeType(FOLDER_MIME_TYPE)
                .sharedWith(new ArrayList<>(OWNER))
                .build();

        return parseFile(fileDbPersistenceService.addFile(fileMetadata));
    }


    public void shareFile(FileShareRequest request) {
        // share file with user
        var file = fileDbPersistenceService.getFileDetails(request.getFileId());
        file.getSharedWith().add(
                UserFileAccess.builder()
                        .user(User.builder()
                                .id(request.getUserId())
                                .build())
                        .access(request.getAccess())
                        .build());

        fileDbPersistenceService.updateFile(file.getId(), file);
    }


}
