package com.sb.upload.download.uploadDownload.repository;

import com.sb.upload.download.uploadDownload.model.FileDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB,String>{
    
}
