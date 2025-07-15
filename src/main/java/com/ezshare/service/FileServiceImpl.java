package com.ezshare.service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ezshare.entity.FileEntity;
import com.ezshare.model.FileModel;
import com.ezshare.repository.FileRepository;
import com.ezshare.exception.FileNotFoundException;

@Service
public class FileServiceImpl implements FileService{

    @Autowired  //dependency injection
    private FileRepository fileRepository;

    private FileModel convertToModel(FileEntity entity) {
        FileModel model = new FileModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }

    @Override
    public List<FileModel> getAll() {
        
        List<FileEntity> entities = fileRepository.findAll();
        return entities.stream().map(this:: convertToModel).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file, String uploadedby) throws IOException {
        
        FileEntity entity = new FileEntity();
        entity.setFilename(file.getOriginalFilename());
        entity.setUploadedBy(uploadedby);
        entity.setUploadTime(LocalDateTime.now());
        entity.setExpiryTime(LocalDateTime.now().plusDays(1));
        entity.setFileData(file.getBytes());
        fileRepository.save(entity);
        return ResponseEntity.ok().body(convertToModel(entity));
    }

    @Override
    public ResponseEntity<?> sharedFile(int id) {
        Optional<FileEntity> entity =fileRepository.findById(id);
        if(entity.isPresent()) {
            return ResponseEntity.ok().body(convertToModel(entity.get()));

        }else{
            throw new FileNotFoundException("File not Found !!!");
        }
    }

    @Override
    public ResponseEntity<?> deleteFile(int id) {
        Optional <FileEntity> entity = fileRepository.findById(id);
        if(entity.isPresent()){
            fileRepository.delete(entity.get());
            return ResponseEntity.ok().body("File Deleted Successfully!");
        }else{
            throw new FileNotFoundException("File not Found !!!");
        }
    }
    
}
