package com.ezshare.service;

import com.ezshare.entity.FileEntity;
import com.ezshare.exception.FileNotFoundException;
import com.ezshare.model.FileModel;
import com.ezshare.repository.FileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for file operations including upload, retrieval, deletion, and sharing.
 * <p>
 * Follows single responsibility principle and delegates persistence to the repository layer.
 * Business logic is handled here; no controller-specific types are returned (i.e., no ResponseEntity).
 */
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    /**
     * Constructor-based dependency injection.
     *
     * @param fileRepository the repository for file persistence operations
     */
    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Converts a FileEntity (JPA) to a FileModel (DTO).
     * Used to decouple persistence and API layers.
     *
     * @param entity the entity to convert
     * @return the DTO model
     */
    private FileModel convertToModel(FileEntity entity) {
        FileModel model = new FileModel();
        BeanUtils.copyProperties(entity, model);
        model.setFileData(null); // Do not expose binary data for list/share endpoints
        return model;
    }

    /**
     * Creates a FileEntity from uploaded file data and uploader info.
     * Initializes metadata such as timestamps.
     *
     * @param file        the uploaded file
     * @param uploadedBy  the user uploading the file
     * @return the entity ready for persistence
     * @throws IOException if file read fails
     */
    private FileEntity convertToEntity(MultipartFile file, String uploadedBy) throws IOException {
        FileEntity entity = new FileEntity();
        entity.setFilename(file.getOriginalFilename());
        entity.setFileData(file.getBytes());
        entity.setUploadedBy(uploadedBy);
        entity.setUploadTime(LocalDateTime.now());
        entity.setExpiryTime(LocalDateTime.now().plusDays(1)); // Set 1-day expiry
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uploadFile(MultipartFile file, String uploadedBy) throws IOException {
        FileEntity entity = convertToEntity(file, uploadedBy);
        fileRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     * @return 
     */
    @Override
    public ResponseEntity deleteFile(int id) {
        if (!fileRepository.existsById(id)) {
            throw new FileNotFoundException("File not found with ID: " + id);
        }
        fileRepository.deleteById(id);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileModel shareFile(int id) {
        return fileRepository.findById(id)
            .map(this::convertToModel)
            .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileModel> getAll() {
        return fileRepository.findAll()
            .stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    /**
     * Fetches the file entity and content for download by ID.
     * Throws if the file is not found.
     *
     * @param id file ID
     * @return the FileModel including binary file data
     */
    @Override
    public FileModel getFile(int id) {
        FileEntity entity = fileRepository.findById(id)
            .orElseThrow(() -> new FileNotFoundException("File not found for download with ID: " + id));
        return convertToModelWithData(entity);
    }

    /**
     * Converts a FileEntity (JPA) to a FileModel (DTO), including file data.
     * Intended for download operations.
     *
     * @param entity the entity
     * @return the DTO including binary data
     */
    private FileModel convertToModelWithData(FileEntity entity) {
        FileModel model = new FileModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }

    /**
     * Scheduled task to delete expired files every hour.
     * This helps free up storage and maintain system hygiene.
     * <p>
     * Cron format: [sec min hour day month weekday]. Here: top of every hour.
     */
    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void deleteExpiredFiles() {
        List<FileEntity> expiredFiles = fileRepository.findByExpiryTimeBefore(LocalDateTime.now());
        expiredFiles.forEach(fileRepository::delete);
        // For production, use logger instead of System.out
        System.out.println("Expired files deleted at " + LocalDateTime.now());
    }
}
