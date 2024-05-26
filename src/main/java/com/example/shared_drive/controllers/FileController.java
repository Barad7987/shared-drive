package com.example.shared_drive.controllers;

import com.example.shared_drive.requests.FileCreateRequest;
import com.example.shared_drive.requests.FileShareRequest;
import com.example.shared_drive.requests.FileUploadRequest;
import com.example.shared_drive.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping
    public ResponseEntity<?> createFile(@RequestBody FileCreateRequest request) {
        var file = fileService.createFile(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(file);
    }

    // considering that at a time only one file can be processed
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(FileUploadRequest request) {
        var file = fileService.uploadFile(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(file);
    }

    @GetMapping
    public ResponseEntity<?> listFiles(@RequestParam(required = false) UUID parentId) {
        return ResponseEntity.ok(fileService.getFiles(parentId));
    }

    @PostMapping("/share")
    public ResponseEntity<?> shareFile(@RequestBody FileShareRequest request) {
        fileService.shareFile(request);
        return ResponseEntity.noContent().build();
    }

}
