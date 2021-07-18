package com.sb.upload.download.uploadDownload.exception;

import com.sb.upload.download.uploadDownload.message.ResponseMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler{
    
@ExceptionHandler(MaxUploadSizeExceededException.class)
public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc){
    ResponseMessage response = new ResponseMessage();
    response.setMessage("file size too large");
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
    
}

}
