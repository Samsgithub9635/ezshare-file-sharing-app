package com.ezshare.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity representing file metadata and content in database.
 */
@Entity
@Data
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String filename;
    private String uploadedBy;

    private LocalDateTime uploadTime;
    private LocalDateTime expiryTime;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData; // Stores raw binary data in DB using BLOB.
}
