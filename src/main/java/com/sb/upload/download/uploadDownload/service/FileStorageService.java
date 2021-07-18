package com.sb.upload.download.uploadDownload.service;

import java.io.IOException;
import java.util.stream.Stream;

import com.sb.upload.download.uploadDownload.model.FileDB;
import com.sb.upload.download.uploadDownload.repository.FileDBRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    
@Autowired
private FileDBRepository fileDBRepository;

public FileDB store(MultipartFile file) {

    
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileDB fileDB = new FileDB();

    try {
        fileDB.setName(fileName);
        fileDB.setType(file.getContentType());
        fileDB.setData(file.getBytes());
        
    } catch (Exception e) {
        //TODO: handle exception
    }
    
    return fileDBRepository.save(fileDB);
}

public FileDB getFile(String id){
    return fileDBRepository.findById(id).get();
}

public Stream<FileDB> getAllFiles(){
    return fileDBRepository.findAll().stream();
}

}
