package com.sb.upload.download.uploadDownload.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sb.upload.download.uploadDownload.message.ResponseFile;
import com.sb.upload.download.uploadDownload.message.ResponseMessage;
import com.sb.upload.download.uploadDownload.model.FileDB;
import com.sb.upload.download.uploadDownload.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileController {
    
@Autowired
private FileStorageService storageService;

@PostMapping("/upload")
public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile[] files){

String message = "";
ResponseMessage response = new ResponseMessage();
List<String> fileNames = new ArrayList<String>();


try {


Arrays.asList(files).stream().forEach(f->{
    storageService.store(f);
    fileNames.add(f.getOriginalFilename());
    
});
message = "upload success with filename: "+fileNames;
    response.setMessage(message);
    return ResponseEntity.status(HttpStatus.OK).body(response);
} catch (Exception e) {
    //TODO: handle exception
    message = "could not upload file with name: "+fileNames;
    response.setMessage(message);
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
}

}

@GetMapping("/files")
public ResponseEntity <List<ResponseFile>> getListFiles(){
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(dbFile.getId()).toUriString();
    
 return new ResponseFile(
     dbFile.getName(),
     fileDownloadUri,
     dbFile.getType(),
     dbFile.getData().length
 );
    
    }).collect(Collectors.toList());

return ResponseEntity.status(HttpStatus.OK).body(files);
}

@GetMapping("/files/{id}")
public ResponseEntity<byte[]> getFile(@PathVariable String id){
    FileDB fileDB = storageService.getFile(id);
return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileDB.getName()+"\"").body(fileDB.getData());

}



}
