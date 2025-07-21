package com.ezshare.service;

import com.ezshare.model.FileModel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    /**
     * Get all files stored in the system.
     */
    List<FileModel> getAll();

    /**
     * Upload a new file.
     * @throws IOException if any file IO error occurs
     */
    void uploadFile(MultipartFile file, String uploadedBy) throws IOException;

    /**
     * Delete a file by ID.
     * @return 
     */
    ResponseEntity<?> deleteFile(int id);

    /**
     * Share a file by ID.
     * Returns a FileModel if found, or null if not found.
     */
    FileModel shareFile(int id);

    /**
     * Get the file's content for download.
     * Returns a FileModel and raw content (details depend on implementation).
     */
    FileModel getFile(int id);

    /**
     * (Optional) Delete expired files.
     */
    void deleteExpiredFiles();
}
