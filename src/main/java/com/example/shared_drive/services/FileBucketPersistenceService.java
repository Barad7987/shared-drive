package com.example.shared_drive.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Service
public class FileBucketPersistenceService {


    private static final String BUCKET_NAME = "shared-drive-bucket";

    public CompletableFuture<String> uploadAsync(MultipartFile file) {
        // use AWS S3 SDK to upload file
        return CompletableFuture.completedFuture("fileKey");
    }

}
