package com.ezshare.model;

import java.time.LocalDateTime;


public class FileModel {
    private int id;
    private String filename;
    private String uploadedBy;
    private LocalDateTime uploadTime;
    private LocalDateTime expiryTime;
    private byte[] fileData;
}
