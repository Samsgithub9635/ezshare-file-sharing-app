package com.ezshare.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ezshare.model.FileModel;

public interface FileService {

    public ResponseEntity<?> uploadFile(MultipartFile file, String uploadedBy) throws IOException;

    public ResponseEntity<?> deleteFile(int id);

    public ResponseEntity<?> shareFile(int id); // ✅ Renamed method

    public List<FileModel> getAll();
}
