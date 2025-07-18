package com.ezshare.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

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
    private byte[] fileData;

}
