package com.ezshare.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ezshare.entity.FileEntity;
import com.ezshare.exception.FileNotFoundException;
import com.ezshare.model.FileModel;
import com.ezshare.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    // Utility method to convert Entity -> Model
    private FileModel convertToModel(FileEntity entity) {
        FileModel model = new FileModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }

    // Utility method to convert MultipartFile -> Entity
    private FileEntity convertToEntity(MultipartFile file, String uploadedBy) throws IOException {
        FileEntity entity = new FileEntity();
        entity.setFilename(file.getOriginalFilename());
        entity.setFileData(file.getBytes());
        entity.setUploadedBy(uploadedBy);
        entity.setUploadTime(LocalDateTime.now());
        entity.setExpiryTime(LocalDateTime.now().plusDays(1));
        return entity;
    }

    // Upload a file and save to DB
    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file, String uploadedBy) throws IOException {
        FileEntity entity = convertToEntity(file, uploadedBy);
        fileRepository.save(entity);
        return ResponseEntity.ok("File uploaded successfully");
    }

    // Delete a file using ID
    @Override
    public ResponseEntity<String> deleteFile(int id) {
        return fileRepository.findById(id)
            .map(file -> {
                fileRepository.deleteById(id);
                return ResponseEntity.ok("Deleted");
            })
            .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + id));
    }

    // Get file data to "share" it (as a DTO/model object)
    @Override
    public ResponseEntity<FileModel> shareFile(int id) {
        return fileRepository.findById(id)
            .map(file -> ResponseEntity.ok(convertToModel(file)))
            .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + id));
    }

    // Return all files as DTOs
    @Override
    public List<FileModel> getAll() {
        return fileRepository.findAll()
                .stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }
}
