package com.ezshare.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ezshare.model.FileModel;

public interface FileService {
    public List<FileModel> getAll();
    public ResponseEntity<?> uploadFile(MultipartFile file, String uploadedby) throws IOException;
    public ResponseEntity<?> sharedFile(int id);
    public ResponseEntity<?> deleteFile(int id);
}
