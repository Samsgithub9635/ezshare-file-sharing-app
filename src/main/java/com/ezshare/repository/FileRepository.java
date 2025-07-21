package com.ezshare.repository;

import com.ezshare.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for FileEntity.
 * Inherits CRUD and query methods from JpaRepository.
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    /**
     * Finds all files that have expired.
     *
     * @param now current time for expiry comparison
     * @return list of expired file entities
     */
    List<FileEntity> findByExpiryTimeBefore(LocalDateTime now);

    // All other CRUD methods (save, findById, deleteById, findAll) are inherited from JpaRepository.
}
