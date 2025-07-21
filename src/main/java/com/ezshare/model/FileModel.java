package com.ezshare.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for files.
 * Used for view and controller responses, decoupled from persistence.
 */
@Data
public class FileModel {
    private int id;
    private String filename;
    private String uploadedBy;
    private LocalDateTime uploadTime;
    private LocalDateTime expiryTime;
    private byte[] fileData; // Null for list/share; included for download endpoint only.
    public boolean hasBody() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasBody'");
    }
    public Object getBody() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBody'");
    }
}
