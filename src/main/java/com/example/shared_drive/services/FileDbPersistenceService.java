package com.example.shared_drive.services;

import com.example.shared_drive.entities.File;
import com.example.shared_drive.enums.FileStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileDbPersistenceService {

    HashMap<UUID, File> files = new HashMap<>();

    public File addFile(File file) {
        file.setId(UUID.randomUUID());
        files.put(file.getId(), file);
        return file;
    }

    public List<File> getRootFiles() {
        return List.copyOf(files.values()
                .stream()
                .filter(file -> file.getParent() == null)
                .toList());
    }

    public File updateFile(UUID id, File file) {
        return files.put(id, file);
    }


    public void updateFileStatus(UUID id, FileStatus status) {
        var file = files.get(id);
        file.setStatus(status);
        files.put(id, file);
    }


    public List<File> getFiles(UUID parentId) {
        // fetch files from database
        if (parentId == null) {
            return getRootFiles();
        }
        return List.copyOf(files.values().stream()
                .filter(file -> file.getParent() != null)
                .filter(file -> parentId.equals(file.getParent().getId()))
                .toList());
    }

    public File getFileDetails(UUID fileId) {
        // fetch file details from database
        return files.get(fileId);
    }

}
