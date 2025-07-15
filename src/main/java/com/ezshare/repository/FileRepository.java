package com.ezshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import com.ezshare.entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findByExpiryDateBefore(LocalDateTime now); // used to expire the data after 24hrs of uploading

    


}    

